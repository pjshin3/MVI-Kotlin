package com.example.mvi_sample

import android.app.Application
import com.example.mvi_sample.di.DaggerAppComponnet

class MainApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        DaggerAppComponnet.builder()
            .aplication(this)
            .build()
            .inject(this)

    }
}