package com.example.mvi_sample.ui.loding.status

import com.example.mvi_sample.base.Interface.IAction

sealed class LodingAction : IAction {

    object InitialUiAction : LodingAction()

    object ServerVersion: LodingAction()
}