package com.example.mvi_sample.ui.login.status

import com.example.mvi_sample.ui.login.ResultDataModel

sealed class LoginResult {

    data class Success(val result: ResultDataModel): LoginResult()
    data class Fail(val error: Throwable?): LoginResult()
    object InFlight : LoginResult()
}