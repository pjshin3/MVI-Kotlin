package com.example.mvi_sample.base

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SchedulerProvider {
    fun io() : Scheduler
    fun ui() : Scheduler
    fun computation() : Scheduler
}

class SchedulerProviderProxy : SchedulerProvider{
    override fun io(): Scheduler = Schedulers.io()

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()

    override fun computation(): Scheduler = Schedulers.computation()
}