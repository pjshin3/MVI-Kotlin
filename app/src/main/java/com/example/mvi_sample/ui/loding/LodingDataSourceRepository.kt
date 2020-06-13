package com.example.mvi_sample.ui.loding

import com.example.mvi_sample.base.BaseRepositoryRemote
import com.example.mvi_sample.base.IRemoteDataSource
import com.example.mvi_sample.base.SchedulerProvider
import io.reactivex.Flowable

class LodingDataSourceRepository (
    remoteDataSource: LodingRemoteDataSource
): BaseRepositoryRemote<LodingRemoteDataSource>(remoteDataSource){

//    fun chack() : Flowable<ServerVersionInfoModel> {
//
//    }

}
class LodingRemoteDataSource(
    private val schedulers: SchedulerProvider
): IRemoteDataSource{
//    fun chack(): Flowable<ServerVersionInfoModel>{
//
//    }
}