package com.example.mvi_sample.ui.loding

import com.example.mvi_sample.base.SchedulerProvider
import com.example.mvi_sample.db.entity.TempDataEntity
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
                    .getChack()
                    .toObservable()
                    .flatMap { onServerCheckSuccess(it) }
            }
        }
    private val lodingGetTempDataTransfomar =
        ObservableTransformer<LodingAction.GetTempData, LodingResult> {action ->
            action.flatMap {
                repository
                    .getData()
                    .toObservable()
                    .flatMap { onGetTempDataSuccess(it) }
            }
        }

    private fun saveData(item: TempDataEntity) =
        repository.
            localDataSource.
            insertData(item)

    private fun onServerCheckSuccess(result: ServerVersionInfoModel) : Observable<LodingResult.SuccessServerCheck> =
        Observable.just(LodingResult.SuccessServerCheck(result.version))
    private fun onGetTempDataSuccess(result: ServerDataModel) : Observable<LodingResult.SuccessGetTempData> =
        Observable
            .just(result)
            .map {
                TempDataEntity(
                    id = it.id,
                    name = it.name,
                    item = it.item,
                    title = it.title,
                    test = it.test,
                    wife = it.wife,
                    age = it.age
                )}
            .map { saveData(it) }
            .map { LodingResult.SuccessGetTempData("KING") }

    private fun onLodingREsultError(error: Throwable) : Observable<LodingResult.Failure> =
        Observable.just(LodingResult.Failure(error))

    internal val actionProcessor =
        ObservableTransformer<LodingAction,LodingResult> {action ->
            action.publish {shard ->
                Observable.merge(
                    shard.ofType(LodingAction.ServerVersion::class.java).compose(lodingTransfomar),
                    shard.ofType(LodingAction.GetTempData::class.java).compose(lodingGetTempDataTransfomar),
                    shard.filter{
                        it !is LodingAction.ServerVersion
                    }.flatMapErrorActionObservable()
                )
            }.retry()
        }
}