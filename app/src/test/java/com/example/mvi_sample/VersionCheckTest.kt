package com.example.mvi_sample

import android.app.Application
import com.example.mvi_sample.di.BASEURL
import com.example.mvi_sample.di.DaggerAppComponnet
import com.example.mvi_sample.di.TIME_OUT_SECONDS
import com.example.mvi_sample.remote.RemoteManager
import com.example.mvi_sample.remote.RetrofitService
import io.reactivex.Flowable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class VersionCheckTest {

    @Inject
    lateinit var retrofitService : RetrofitService

    lateinit var retrofit : Retrofit

    @Before
    fun setUp(){
        DaggerAppComponnet.builder()
            .aplication(Application())
            .build()
            .inject(MainApplication())

       val okHttpClient =  OkHttpClient.Builder()
            .connectTimeout(
                TIME_OUT_SECONDS.toLong(),
                TimeUnit.SECONDS
            )
            .readTimeout(
                TIME_OUT_SECONDS.toLong(),
                TimeUnit.SECONDS
            )
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = when (BuildConfig.DEBUG){
                        true -> HttpLoggingInterceptor.Level.BODY
                        false -> HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .build()


       retrofit =  Retrofit.Builder()
            .baseUrl(BASEURL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Test
    fun `TEST VERSION CHECK`(){
        retrofit.create(RetrofitService::class.java).getSerVerinfo().subscribe({
            println("성공 ${it.version}")
            Assert.assertEquals("1",it.version)
        },{
            println("실패 $it")
        })
    }

    @Test
    fun `TEST GET DATA`(){
        retrofit
            .create(RetrofitService::class.java)
            .getData()
            .subscribe({
                println(it.wife)
                Assert.assertEquals("hong", it.wife)
                },{
            })
    }
}