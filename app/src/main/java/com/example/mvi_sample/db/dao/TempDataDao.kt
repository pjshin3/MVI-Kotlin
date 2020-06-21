package com.example.mvi_sample.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvi_sample.db.entity.TempDataEntity

@Dao
interface TempDataDao {
    @Query("SELECT * FROM tempdata")
    fun getTempDataAll() : LiveData<TempDataEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTempData(item : TempDataEntity)
}