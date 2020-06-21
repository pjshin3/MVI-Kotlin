package com.example.mvi_sample.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tempdata")
data class TempDataEntity(
    @PrimaryKey
    val id : String,
    val name : String,
    val item : String,
    val title : String,
    val test : String,
    val wife : String,
    val age : Int
) {
    fun getUniqueId() = id
}