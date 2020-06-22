package com.example.mvi_sample.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.mvi_sample.MainApplication
import com.example.mvi_sample.db.AppDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule() {

    @Singleton
    @Provides
    fun providesDatabase(context: Application) =
        Room.databaseBuilder(context, AppDataBase::class.java,"my_data")

}