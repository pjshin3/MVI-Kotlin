package com.example.mvi_sample.di

import android.app.Application
import com.example.mvi_sample.MainApplication
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponnet{

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun aplication(application: Application): Builder

        fun build(): AppComponnet
    }

    fun inject(application: MainApplication)
}