package com.example.mvi_sample.ui.loding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvi_sample.base.BaseViewModel
import com.example.mvi_sample.ex.notOfType
import com.example.mvi_sample.remote.ProcessorHolder
import com.example.mvi_sample.ui.loding.status.LodingAction
import com.example.mvi_sample.ui.loding.status.LodingIntent
import com.example.mvi_sample.ui.loding.status.LodingResult
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.PublishSubject

class LodingViewModel (
    private val proecessorHolder : ProcessorHolder
): BaseViewModel<LodingIntent,LodingViewState>(){

    private val intentsSubject: PublishSubject<LodingIntent> = PublishSubject.create()
    private val statesObservable: Observable<LodingViewState> = compose()

    private val intentFilter: ObservableTransformer<LodingIntent, LodingIntent>
        get() =  ObservableTransformer { intents ->
            intents.publish {shared ->
                Observable.merge(
                    shared.ofType(LodingIntent.InitialIntent::class.java).take(1),
                    shared.notOfType(LodingIntent.InitialIntent::class.java)
                )
            }
        }


    override fun processIntents(intents: Observable<LodingIntent>) {
        intents.autoDisposable(this).subscribe(intentsSubject)
    }

    override fun states(): Observable<LodingViewState> = statesObservable


    private fun compose()=
        intentsSubject
            .compose(intentFilter)
            .map { actionFromIntent(it) }
            .compose(proecessorHolder.actionLodingProcessor)
            .scan(LodingViewState.idle(), reducer)
            .switchMap(specialEventProcessor)
            .distinctUntilChanged()
            .replay(1)
            .autoConnect(0)

    private fun actionFromIntent(intent: LodingIntent): LodingAction {
        return when(intent){
            is LodingIntent.InitialIntent -> LodingAction.InitialUiAction
            is LodingIntent.getServerInfo -> LodingAction.ServerVersion
        }
    }

    private val  specialEventProcessor : io.reactivex.functions.Function<LodingViewState, ObservableSource<LodingViewState>>
        get() = io.reactivex.functions.Function { state ->
            when(state.uiEvents != null || state.errors != null){
                true -> Observable.just(state,state.copy(uiEvents = null , errors = null))
                false -> Observable.just(state)
            }
        }

    companion object{

        private val reducer = io.reactivex.functions.BiFunction{ proviusState: LodingViewState, result: LodingResult ->
            when(result){
                is LodingResult.SuccessServerCheck -> {
                    proviusState.copy(
                        isLoading = false,
                        errors = null,
                        uiEvents = LodingViewState.LodingUiEvents.JumpMain(result.version)
                    )}
                is LodingResult.Failure ->{
                    proviusState.copy(
                        isLoading = false,
                        errors = result.error,
                        uiEvents = null
                    )}
                is LodingResult.InFlight ->{
                    proviusState.copy(
                        isLoading = true,
                        errors = null,
                        uiEvents = null
                    )
                }
             }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class LodingViewModelFactory(
        private val processorHolder: ProcessorHolder
    ): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            LodingViewModel(processorHolder) as T
    }
}