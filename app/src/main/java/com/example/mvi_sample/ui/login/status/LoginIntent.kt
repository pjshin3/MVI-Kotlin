package com.example.mvi_sample.ui.login.status

import com.example.mvi_sample.base.Interface.IIntent

sealed class LoginIntent : IIntent {

    object InitialIntent: LoginIntent()

    data class LoginButtonClickIntent(
        val id : String?,
        val password: String?
    ): LoginIntent()
}