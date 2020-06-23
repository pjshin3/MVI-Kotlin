package com.example.mvi_sample.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.mvi_sample.BuildConfig
import com.example.mvi_sample.MainActivity
import com.example.mvi_sample.base.SchedulerProvider
import com.example.mvi_sample.base.SchedulerProviderProxy
import com.example.mvi_sample.db.AppDataBase
import com.example.mvi_sample.remote.RemoteManager
import com.example.mvi_sample.remote.RetrofitService
import com.example.mvi_sample.ui.loding.LodingLocalDataSource
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
const val BASEURL = "http://192.168.35.150:3000"

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

    @Singleton
    @Provides
    fun providesSchedulerProvider(): SchedulerProvider{
        return SchedulerProviderProxy()
    }

    @Singleton
    @Provides
    fun providesRemoteService(retrofit: Retrofit) : RetrofitService{
        return retrofit.create(RetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun providesRemoteManager(retrofitService: RetrofitService): RemoteManager{
        return RemoteManager(retrofitService = retrofitService)
    }

    @Singleton
    @Provides
    fun providesDatabase(context: Application) : AppDataBase =
        Room.databaseBuilder(context, AppDataBase::class.java,"my_data").build()
}