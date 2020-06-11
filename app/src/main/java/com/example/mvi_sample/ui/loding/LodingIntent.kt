package com.example.mvi_sample.ui.loding

import com.example.mvi_sample.base.IIntent

sealed class LodingIntent : IIntent{

    object InitialIntent : LodingIntent()

    data class LodingClicksIntent(
        val item : String?,
        val item1 : String
    ): LodingIntent()
}