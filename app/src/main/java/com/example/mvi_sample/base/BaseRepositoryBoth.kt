package com.example.mvi_sample.base

open class BaseRepositoryBoth<T: IRemoteDataSource, R: ILocalDataSource>(
        val remoteDataSource: T,
        val localDataSource: R
) : IRepository

open class BaseRepositoryRemote <T : IRemoteDataSource>(
        val remoteDataSource: T
) : IRepository