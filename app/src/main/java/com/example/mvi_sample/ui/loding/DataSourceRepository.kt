package com.example.mvi_sample.ui.loding

import com.example.mvi_sample.base.BaseRepositoryRemote
import com.example.mvi_sample.base.Interface.IRemoteDataSource
import com.example.mvi_sample.base.SchedulerProvider
import com.example.mvi_sample.remote.RemoteManager
import com.example.mvi_sample.ui.login.ResultDataModel
import io.reactivex.Flowable

class DataSourceRepository (
    remoteDataSource: RemoteDataSource
): BaseRepositoryRemote<RemoteDataSource>(remoteDataSource){

    fun getChack() : Flowable<ServerVersionInfoModel> =
        remoteDataSource
            .getChack()
            .doOnNext {it}


    fun getData() : Flowable<ResultDataModel> =
        remoteDataSource
            .getData()
            .doOnNext{it}

}
class RemoteDataSource(
    private val remoteManager: RemoteManager,
    private val schedulers: SchedulerProvider
): IRemoteDataSource {

    fun getChack(): Flowable<ServerVersionInfoModel> =
        remoteManager
            .retrofitService
            .getSerVerinfo()
            .subscribeOn(schedulers.io())

    fun getData(): Flowable<ResultDataModel> =
        remoteManager
            .retrofitService
            .getData()
            .subscribeOn(schedulers.io())
}
