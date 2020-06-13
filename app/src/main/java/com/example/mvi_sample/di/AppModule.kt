package com.example.mvi_sample.di

import com.example.mvi_sample.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val TIME_OUT_SECONDS = 10
const val BASEURL = "192.168.35.150"

@Module
class AppModule {

    @Singleton
    @Provides
    fun providerOkhttpClient() =
        OkHttpClient.Builder()
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

    @Singleton
    @Provides
    fun providerRetrofit(okHttpClient: OkHttpClient): Retrofit =
         Retrofit.Builder()
            .baseUrl(BASEURL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}