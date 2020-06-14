package com.example.mvi_sample.ui.loding.state

import com.example.mvi_sample.base.Interface.IIntent

sealed class LodingIntent : IIntent {

    object InitialIntent : LodingIntent()

    object Start : LodingIntent()
}