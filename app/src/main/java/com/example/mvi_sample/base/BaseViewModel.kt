package com.example.mvi_sample.base

import com.example.mvi_sample.base.Interface.IIntent
import com.example.mvi_sample.base.Interface.IViewModel
import com.example.mvi_sample.base.Interface.IViewState

abstract class BaseViewModel <I : IIntent, S: IViewState> : AutoDisposeViewModel() ,
    IViewModel<I, S>