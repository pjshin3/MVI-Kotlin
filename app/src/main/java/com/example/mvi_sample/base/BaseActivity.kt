package com.example.mvi_sample.base

import android.os.Bundle

abstract class BaseActivity <I : IIntent, in S: IViewState> : AutoDisposeActivity()
    , IView<I,S> {
    abstract val layoutId : Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
    }
}