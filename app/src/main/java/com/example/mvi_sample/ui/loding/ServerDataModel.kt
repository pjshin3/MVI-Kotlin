package com.example.mvi_sample.ui.loding

import com.google.gson.annotations.SerializedName

data class ServerDataModel(
    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("item") val item : String,
    @SerializedName("title") val title : String,
    @SerializedName("test") val test : String,
    @SerializedName("wife") val wife : String,
    @SerializedName("age") val age : Int
)