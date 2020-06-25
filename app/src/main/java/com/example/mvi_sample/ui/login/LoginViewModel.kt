package com.example.mvi_sample.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvi_sample.base.BaseViewModel
import com.example.mvi_sample.ex.notOfType
import com.example.mvi_sample.remote.ProcessorHolder
import com.example.mvi_sample.ui.loding.LodingViewState
import com.example.mvi_sample.ui.login.status.LoginAction
import com.example.mvi_sample.ui.login.status.LoginIntent
import com.example.mvi_sample.ui.login.status.LoginResult
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.PublishSubject
import java.util.function.BiFunction

class LoginViewModel(
    private val proecessorHolder : ProcessorHolder
) : BaseViewModel<LoginIntent,LoginViewState>(){

    private val intentsSubject: PublishSubject<LoginIntent> = PublishSubject.create()
    private val stateObservable: Observable<LoginViewState> = compose()

    override fun processIntents(intents: Observable<LoginIntent>) =
        intents.autoDisposable(this).subscribe(intentsSubject)


    override fun states(): Observable<LoginViewState> = stateObservable

    private val intentFilter: ObservableTransformer<LoginIntent,LoginIntent> =
        ObservableTransformer { intent ->
            intent.publish { shared ->
                Observable.merge(
                    shared.ofType(LoginIntent.InitialIntent::class.java).take(1),
                    shared.notOfType(LoginIntent.InitialIntent::class.java)
                )
            }
        }

    private fun actionFromIntent(intent: LoginIntent): LoginAction{
        return when(intent){
            is LoginIntent.InitialIntent -> LoginAction.InitialUiAction
            is LoginIntent.LoginButtonClickIntent -> LoginAction.SendToLoginInfo(intent.id,intent.password)
        }
    }

    private fun compose() =
        intentsSubject
            .compose(intentFilter)
            .map(this::actionFromIntent)
            .compose(proecessorHolder.actionLoginProcessor)
            .scan(LoginViewState.idle(), reducer)


    companion object{

        private val reducer = io.reactivex.functions.BiFunction{ proviusState: LoginViewState, result: LoginResult ->
            when(result){
                is LoginResult.Success -> {
                    proviusState.copy(
                        isLoding = false,
                        errors = null,
                        uiEvents = LoginViewState.LoginUiEvent.SucessLogin(result.result)
                    )
                }
                is LoginResult.Fail -> {
                    proviusState.copy(
                        isLoding = false,
                        errors = result.error,
                        uiEvents = null
                    )
                }
                is LoginResult.InFlight -> {
                    proviusState.copy(
                        isLoding = false,
                        errors = null,
                        uiEvents = null
                    )
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class LoginViewModelFactory(
        private val processorHolder: ProcessorHolder
    ): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            LoginViewModel(processorHolder) as T
    }

}