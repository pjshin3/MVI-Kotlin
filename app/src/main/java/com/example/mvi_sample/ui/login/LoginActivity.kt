package com.example.mvi_sample.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.mvi_sample.MainActivity
import com.example.mvi_sample.R
import com.example.mvi_sample.base.BaseActivity
import com.example.mvi_sample.ui.login.status.LoginIntent
import com.jakewharton.rxbinding3.view.clicks
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity<LoginIntent,LoginViewState>(){

    @Inject
    lateinit var viewModel: LoginViewModel

    override val layoutId = R.layout.activity_login

    private val clickLoginButton =
        PublishSubject.create<LoginIntent.LoginButtonClickIntent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind()
    }

    private fun bind(){
        bt.clicks()
            .map { LoginIntent.LoginButtonClickIntent("","") }
            .autoDisposable(scopeProvider)
            .subscribe(clickLoginButton)

        viewModel.states()
            .autoDisposable(scopeProvider)
            .subscribe(this::render)

        viewModel.processIntents(intents())
    }


    override fun intents(): Observable<LoginIntent>  = Observable.mergeArray<LoginIntent>(
        clickLoginButton
    ).startWith(LoginIntent.InitialIntent)

    override fun render(states: LoginViewState) {
        when(states.uiEvents){
            is LoginViewState.LoginUiEvent.SucessLogin -> {
                Toast.makeText(this, "로그인 성공 ${states.uiEvents.result}",Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object{
        fun launch(activity: FragmentActivity){
            activity.startActivity(Intent(activity, LoginActivity::class.java))
        }
    }


}