package com.example.mvi_sample.remote

import com.example.mvi_sample.ui.loding.ServerVersionInfoModel
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Headers

interface RetrofitService {
    @GET("servserinfo")
    @Headers("Accept: application/json")
    fun getSerVerinfo() : Flowable<ServerVersionInfoModel>

}