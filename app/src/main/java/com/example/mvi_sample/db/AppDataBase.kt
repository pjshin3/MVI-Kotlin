package com.example.mvi_sample.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvi_sample.db.dao.TempDataDao
import com.example.mvi_sample.db.entity.TempDataEntity

@Database(entities = [TempDataEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase(){
    abstract fun getTempDao() : TempDataDao
}