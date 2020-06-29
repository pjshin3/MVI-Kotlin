package com.example.mvi_sample.di.ui

import androidx.lifecycle.ViewModelProvider
import com.example.mvi_sample.base.SchedulerProvider
import com.example.mvi_sample.di.ActivityScope
import com.example.mvi_sample.remote.ProcessorHolder
import com.example.mvi_sample.remote.RemoteManager
import com.example.mvi_sample.remote.RemoteDataSourceRepository
import com.example.mvi_sample.remote.RemoteDataSource
import com.example.mvi_sample.ui.login.LoginActivity
import com.example.mvi_sample.ui.login.LoginViewModel
import dagger.Module
import dagger.Provides


@Module
class LoginActivityModule {

    @ActivityScope
    @Provides
    fun providesViewmdoel(
        activity: LoginActivity,
        processorHolder: ProcessorHolder
    ): LoginViewModel =
        ViewModelProvider(activity,LoginViewModel.LoginViewModelFactory(processorHolder)).get(LoginViewModel::class.java)

    @ActivityScope
    @Provides
    fun providesLoginActionProcessorHolder(
        repositoryRemote: RemoteDataSourceRepository,
        schedulerProvider: SchedulerProvider
    ): ProcessorHolder {
        return ProcessorHolder(
            repositoryRemote,
            schedulerProvider
        )
    }

    @ActivityScope
    @Provides
    fun providesLoginRepository(
        remoteManager: RemoteManager,
        schedulerProvider: SchedulerProvider
    ): RemoteDataSourceRepository {
        return RemoteDataSourceRepository(
            RemoteDataSource(
                remoteManager,
                schedulerProvider
            )
        )
    }
}