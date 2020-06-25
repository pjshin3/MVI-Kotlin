package com.example.mvi_sample.di.ui

import androidx.lifecycle.ViewModelProvider
import com.example.mvi_sample.base.SchedulerProvider
import com.example.mvi_sample.di.ActivityScope
import com.example.mvi_sample.remote.ProcessorHolder
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
        processorHolder: ProcessorHolder
    ): LodingViewModel{
        return ViewModelProvider(activity,LodingViewModel.LodingViewModelFactory(processorHolder)).get(LodingViewModel::class.java)
    }

    @ActivityScope
    @Provides
    fun providesLodingActionProcessorHolder(
        repository: DataSourceRepository,
        schedulerProvider: SchedulerProvider
    ): ProcessorHolder {
        return ProcessorHolder(
            repository,
            schedulerProvider
        )
    }

    @ActivityScope
    @Provides
    fun providesLodingRepository(
        remoteManager: RemoteManager,
        schedulerProvider: SchedulerProvider
    ):DataSourceRepository{
        return DataSourceRepository(RemoteDataSource(remoteManager,schedulerProvider))
    }
}