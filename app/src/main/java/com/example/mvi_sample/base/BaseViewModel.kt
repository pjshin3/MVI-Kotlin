package com.example.mvi_sample.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel <I : IIntent, S: IViewState> : AutoDisposeViewModel() ,IViewModel<I,S>