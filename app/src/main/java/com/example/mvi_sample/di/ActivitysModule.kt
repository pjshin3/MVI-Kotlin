package com.example.mvi_sample.di

import com.example.mvi_sample.di.ui.LodingActivityModule
import com.example.mvi_sample.di.ui.LoginActivityModule
import com.example.mvi_sample.ui.loding.LodingActivity
import com.example.mvi_sample.ui.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [LodingActivityModule::class])
    abstract fun contributesLodingActivity(): LodingActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    abstract fun contributesLoginActivity(): LoginActivity
}