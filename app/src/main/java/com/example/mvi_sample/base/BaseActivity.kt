package com.example.mvi_sample.base

import android.os.Bundle
import com.example.mvi_sample.base.Interface.IIntent
import com.example.mvi_sample.base.Interface.IView
import com.example.mvi_sample.base.Interface.IViewState

abstract class BaseActivity <I : IIntent, in S: IViewState> : InjectionActivity()
    , IView<I, S> {
    abstract val layoutId : Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
    }
}