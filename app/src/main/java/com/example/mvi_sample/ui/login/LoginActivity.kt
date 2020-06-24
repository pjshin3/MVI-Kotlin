package com.example.mvi_sample.ui.login

import android.os.Bundle
import android.widget.Toast
import com.example.mvi_sample.R
import com.example.mvi_sample.base.BaseActivity
import com.example.mvi_sample.ui.login.status.LoginIntent
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class LoginActivity : BaseActivity<LoginIntent,LoginViewState>(){

    override val layoutId = R.layout.activity_login

    private val ClickLoginButton =
        PublishSubject.create<LoginIntent.LoginButtonClick>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind()
    }

    private fun bind(){

    }


    override fun intents(): Observable<LoginIntent>  = Observable.mergeArray<LoginIntent>(
        ClickLoginButton
    ).startWith(LoginIntent.InitialIntent)

    override fun render(states: LoginViewState) {
        when(states.uiEvents){
            is LoginViewState.LoginUiEvent.SucessLogin -> {
                Toast.makeText(this, "로그인 성공 ${states.uiEvents.response}",Toast.LENGTH_LONG).show()
            }
        }
    }

}