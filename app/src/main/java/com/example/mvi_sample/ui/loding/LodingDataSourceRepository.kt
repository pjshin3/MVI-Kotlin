package com.example.mvi_sample.ui.loding

import com.example.mvi_sample.base.BaseRepositoryRemote
import com.example.mvi_sample.base.Interface.IRemoteDataSource
import com.example.mvi_sample.base.SchedulerProvider
import com.example.mvi_sample.remote.RemoteManager
import io.reactivex.Flowable

class LodingDataSourceRepository (
    remoteDataSource: LodingRemoteDataSource
): BaseRepositoryRemote<LodingRemoteDataSource>(remoteDataSource){

    fun getChack() : Flowable<ServerVersionInfoModel> {
        return remoteDataSource.getChack()
            .doOnNext { it }
    }
    fun getData() : Flowable<ServerDataModel> =
        remoteDataSource
            .getData()
            .doOnNext { it }


}
class LodingRemoteDataSource(
    private val remoteManager: RemoteManager,
    private val schedulers: SchedulerProvider
): IRemoteDataSource {

    fun getChack(): Flowable<ServerVersionInfoModel> =
        remoteManager
            .retrofitService
            .getSerVerinfo()
            .subscribeOn(schedulers.io())

    fun getData(): Flowable<ServerDataModel> =
            remoteManager
                .retrofitService
                .getData()
                .subscribeOn(schedulers.io())
}