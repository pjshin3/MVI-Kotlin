package com.example.mvi_sample.ui.loding.state

import com.example.mvi_sample.base.IAction

sealed class LodingAction : IAction{

    object InitialUiAction : LodingAction()

    object ServerVersion: LodingAction()
}