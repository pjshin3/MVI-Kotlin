package com.example.mvi_sample.ui.loding

import com.example.mvi_sample.base.Interface.IViewState

data class LodingViewState(
    val isLoading: Boolean,
    val errors: Throwable?,
    val uiEvents: LodingUiEvents?
): IViewState {

    sealed class LodingUiEvents {

        data class JumpMain(val vsersionCode: String) : LodingUiEvents()
        data class SaveData(val result: String): LodingUiEvents()
    }

    companion object{

        fun idle(): LodingViewState = LodingViewState(
            isLoading = false,
            errors = null,
            uiEvents = null
        )
    }
}