package com.example.mvi_sample.ui.loding

import com.example.mvi_sample.base.BaseViewModel
import com.example.mvi_sample.ex.notOfType
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.PublishSubject

class LodingViewModel (
): BaseViewModel<LodingIntent,LodingViewState>(){

    private val intentsSubject: PublishSubject<LodingIntent> = PublishSubject.create()
    private val statesObservable: Observable<LodingViewState> = TODO()
//    compose()

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


//    private fun compose(){
//        return intentsSubject
//            .compose(intentFilter)
//            .map(this::actionFromIntent)
//            .
//    }

    private fun actionFromIntent(intent: LodingIntent): LodingAction{
        return when(intent){
            is LodingIntent.InitialIntent -> LodingAction.InitialUiAction
            is LodingIntent.Start -> LodingAction.ServerVersion
        }
    }

}