package com.example.mvi_sample.base

import io.reactivex.Observable


interface IViewModel<I : IIntent, VS: IViewState>{
    fun processIntents(intents: Observable<I>)
    fun states(): Observable<VS>
}