package com.example.mvi_sample.base

import io.reactivex.Observable

interface IView <I : IIntent, in S : IViewState>{

    fun intents():Observable<I>

    fun render(states: S)
}