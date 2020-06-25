package com.example.mvi_sample.ui.login

import com.example.mvi_sample.base.Interface.IViewState
import com.example.mvi_sample.ui.loding.LodingViewState

data class LoginViewState(
    val isLoding: Boolean,
    val errors: Throwable?,
    val uiEvents: LoginUiEvent?
): IViewState{

    sealed class LoginUiEvent{

        data class SucessLogin( val result: ResultDataModel) : LoginUiEvent()
    }

    companion object{

        fun idle(): LoginViewState = LoginViewState(
            isLoding = false,
            errors = null,
            uiEvents = null
        )
    }

}