package com.example.mvi_sample.ui.login.status

import com.example.mvi_sample.base.Interface.IAction

sealed class LoginAction : IAction{

    object InitialUiAction : LoginAction()
    object SendToLoginInfo : LoginAction()
}