package com.example.mvi_sample.base

import com.example.mvi_sample.base.Interface.ILocalDataSource
import com.example.mvi_sample.base.Interface.IRemoteDataSource
import com.example.mvi_sample.base.Interface.IRepository

open class BaseRepositoryBoth<T: IRemoteDataSource, R: ILocalDataSource>(
        val remoteDataSource: T,
        val localDataSource: R
) : IRepository

open class BaseRepositoryRemote <T : IRemoteDataSource>(
        val remoteDataSource: T
) : IRepository