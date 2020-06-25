package com.example.mvi_sample.remote

import com.example.mvi_sample.base.SchedulerProvider
import com.example.mvi_sample.ex.flatMapErrorActionObservable
import com.example.mvi_sample.ui.loding.DataSourceRepository
import com.example.mvi_sample.ui.loding.ServerVersionInfoModel
import com.example.mvi_sample.ui.loding.status.LodingAction
import com.example.mvi_sample.ui.loding.status.LodingResult
import com.example.mvi_sample.ui.login.ResultDataModel
import com.example.mvi_sample.ui.login.status.LoginAction
import com.example.mvi_sample.ui.login.status.LoginResult
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class ProcessorHolder (
    private val repository : DataSourceRepository,
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

    private val logineTransfomar =
        ObservableTransformer<LoginAction.SendToLoginInfo, LoginResult> {action ->
            action.flatMap {
                repository
                    .getData()
                    .toObservable()
                    .flatMap { onGetDataSuccess(it) }
            }
        }

    private fun onServerCheckSuccess(result: ServerVersionInfoModel) : Observable<LodingResult.SuccessServerCheck> =
        Observable.just(LodingResult.SuccessServerCheck(result.version))

    private fun onGetDataSuccess(result: ResultDataModel): Observable<LoginResult.Success> =
        Observable.just(LoginResult.Success(result))

    internal val actionLodingProcessor =
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
    internal val actionLoginProcessor =
        ObservableTransformer<LoginAction, LoginResult> {action ->
            action.publish {shard ->
                Observable.merge(
                    shard.ofType(LoginAction.SendToLoginInfo::class.java).compose(logineTransfomar),
                    shard.filter {
                        it !is LoginAction.SendToLoginInfo
                    }.flatMapErrorActionObservable()
                )
            }
        }
}