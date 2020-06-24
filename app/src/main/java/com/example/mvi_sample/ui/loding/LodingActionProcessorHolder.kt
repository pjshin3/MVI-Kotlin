package com.example.mvi_sample.ui.loding

import com.example.mvi_sample.base.SchedulerProvider
import com.example.mvi_sample.db.entity.TempDataEntity
import com.example.mvi_sample.ex.flatMapErrorActionObservable
import com.example.mvi_sample.ui.loding.status.LodingAction
import com.example.mvi_sample.ui.loding.status.LodingResult
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
                    .getChack()
                    .toObservable()
                    .flatMap { onServerCheckSuccess(it) }
            }
        }

    private fun onServerCheckSuccess(result: ServerVersionInfoModel) : Observable<LodingResult.SuccessServerCheck> =
        Observable.just(LodingResult.SuccessServerCheck(result.version))

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