package com.example.mvi_sample.remote

import com.example.mvi_sample.ui.loding.ServerVersionInfoModel
import io.reactivex.Flowable
import retrofit2.http.GET

interface RetrofitService {
    @GET("/serverinfo")
    fun getSerVerinfo() : Flowable<ServerVersionInfoModel>

}