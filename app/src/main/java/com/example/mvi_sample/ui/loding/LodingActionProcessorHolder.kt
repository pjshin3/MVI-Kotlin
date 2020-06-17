package com.example.mvi_sample.ui.loding

import android.util.Log
import com.example.mvi_sample.base.SchedulerProvider
import com.example.mvi_sample.ex.flatMapErrorActionObservable
import com.example.mvi_sample.ui.loding.state.LodingAction
import com.example.mvi_sample.ui.loding.state.LodingResult
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class LodingActionProcessorHolder (
    private val repository : LodingDataSourceRepository,
    private val scheduler: SchedulerProvider
) {

    private val lodingTransfomar =
        ObservableTransformer<LodingAction.ServerVersion, LodingResult> {action ->
            action.flatMap {
                repository
                    .chack()
                    .toObservable()
                    .flatMap {
                        Log.e("philip","테스트 $it")
                        onLodingResultSuccess(it)
                    }
                    .subscribeOn(scheduler.io())
                    .observeOn(scheduler.ui())
                    .startWith{LodingResult.InFlight}
            }
        }

    private fun onLodingResultSuccess(result: ServerVersionInfoModel) : Observable<LodingResult.Success> =
        Observable.just(LodingResult.Success(result.version))

    private fun onLodingREsultError(error: Throwable) : Observable<LodingResult.Failure> =
        Observable.just(LodingResult.Failure(error))

    internal val actionProcessor =
        ObservableTransformer<LodingAction,LodingResult> {action ->
            action.publish {shard ->
                Observable.merge(
                    shard.ofType(LodingAction.ServerVersion::class.java).compose(lodingTransfomar),
                    shard.filter{
                        it !is LodingAction.ServerVersion
                    }.flatMapErrorActionObservable()
                )
            }.retry()
        }
}