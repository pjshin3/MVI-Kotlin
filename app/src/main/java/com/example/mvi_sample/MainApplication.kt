package com.example.mvi_sample

import android.app.Application
import com.example.mvi_sample.di.DaggerAppComponnet
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainApplication : Application(),HasAndroidInjector{

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector


    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        DaggerAppComponnet.builder()
            .aplication(this)
            .build()
            .inject(this)

    }

    companion object{
        lateinit var INSTANCE: MainApplication
    }
}