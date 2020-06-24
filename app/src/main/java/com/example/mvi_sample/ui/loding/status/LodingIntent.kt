package com.example.mvi_sample.ui.loding.status

import com.example.mvi_sample.base.Interface.IIntent

sealed class LodingIntent : IIntent {

    object InitialIntent : LodingIntent()

    object getServerInfo : LodingIntent()
}