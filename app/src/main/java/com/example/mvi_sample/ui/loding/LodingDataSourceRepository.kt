package com.example.mvi_sample.ui.loding

import android.util.Log
import com.example.mvi_sample.base.BaseRepositoryRemote
import com.example.mvi_sample.base.Interface.IRemoteDataSource
import com.example.mvi_sample.base.SchedulerProvider
import com.example.mvi_sample.remote.RemoteManager
import io.reactivex.Flowable

class LodingDataSourceRepository (
    remoteDataSource: LodingRemoteDataSource
): BaseRepositoryRemote<LodingRemoteDataSource>(remoteDataSource){

    fun chack() : Flowable<ServerVersionInfoModel> {
        return remoteDataSource.chack()
            .doOnNext { it }
    }

}
class LodingRemoteDataSource(
    private val remoteManager: RemoteManager,
    private val schedulers: SchedulerProvider
): IRemoteDataSource {
    fun chack(): Flowable<ServerVersionInfoModel>{
        val autoObservable = remoteManager.retrofitService
            .getSerVerinfo()

        return autoObservable
            .subscribeOn(schedulers.io())
    }
}