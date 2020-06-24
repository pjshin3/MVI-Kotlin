package com.example.mvi_sample.ui.login

import com.google.gson.annotations.SerializedName

data class ResultDataModel(
    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("item") val item : String,
    @SerializedName("title") val title : String,
    @SerializedName("test") val test : String,
    @SerializedName("wife") val wife : String,
    @SerializedName("age") val age : Int
)