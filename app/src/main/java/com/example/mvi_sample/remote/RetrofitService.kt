package com.example.mvi_sample.remote

import com.example.mvi_sample.ui.loding.ServerVersionInfoModel
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Headers

interface RetrofitService {
    @GET("/myAppServerChack")
    @Headers("Accept: text/html")
    fun getSerVerinfo() : Flowable<ServerVersionInfoModel>

}