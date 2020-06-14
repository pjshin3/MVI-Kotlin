package com.example.mvi_sample.di.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.mvi_sample.base.SchedulerProvider
import com.example.mvi_sample.di.ActivityScope
import com.example.mvi_sample.remote.RemoteManager
import com.example.mvi_sample.ui.loding.*
import dagger.Module
import dagger.Provides

@Module
class LodingActivityModule {

    @ActivityScope
    @Provides
    fun providesViewModel(
        activity: LodingActivity,
        processorHolder: LodingActionProcessorHolder
    ): LodingViewModel{
        return ViewModelProvider(activity,LodingViewModel.LodingViewModelFactory(processorHolder))[LodingViewModel::class.java]
    }

    @ActivityScope
    @Provides
    fun providesLodingActionProcessorHolder(
        repository: LodingDataSourceRepository,
        schedulerProvider: SchedulerProvider
    ): LodingActionProcessorHolder{
        return LodingActionProcessorHolder(repository,schedulerProvider)
    }

    @ActivityScope
    @Provides
    fun providesLodingRepository(
        remoteManager: RemoteManager,
        schedulerProvider: SchedulerProvider
    ):LodingDataSourceRepository{
        return LodingDataSourceRepository(
            remoteDataSource = LodingRemoteDataSource(remoteManager,schedulerProvider)
        )
    }

}