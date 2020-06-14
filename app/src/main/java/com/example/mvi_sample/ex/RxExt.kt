package com.example.mvi_sample.ex

import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.annotations.SchedulerSupport
import java.lang.IllegalArgumentException


@CheckReturnValue
@SchedulerSupport(SchedulerSupport.NONE)
fun <T: Any, U : Any> Observable<T>.notOfType(clazz: Class<U>): Observable<T>{
    checkNotNull(clazz) {"clazz is null"}
    return filter { !clazz.isInstance(it) }
}

@CheckReturnValue
@SchedulerSupport(SchedulerSupport.NONE)
fun <T: Any, E: Any> Observable<E>.flatMapErrorActionObservable(): Observable<T> =
    this.flatMap { action ->
        Observable.error<T>(IllegalArgumentException("Unknown Action type: $action"))
    }