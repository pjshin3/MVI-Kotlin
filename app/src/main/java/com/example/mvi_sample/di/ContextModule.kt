package com.example.mvi_sample.di

import android.app.Application
import android.content.Context
import com.example.mvi_sample.MainApplication
import dagger.Module
import dagger.Provides

@Module
object ContextModule {
    @Provides
    @JvmStatic
    internal fun providesContext(context: Context) =
        context as Application
}