package com.example.mvi_sample.ui.login

import com.example.mvi_sample.base.BaseViewModel
import com.example.mvi_sample.ui.login.status.LoginIntent
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class LoginViewModel : BaseViewModel<LoginIntent,LoginViewState>(){

    private val intentsSubject: PublishSubject<LoginIntent> = PublishSubject.create()


    override fun processIntents(intents: Observable<LoginIntent>) {
        TODO("Not yet implemented")
    }

    override fun states(): Observable<LoginViewState> {
        TODO("Not yet implemented")
    }

}