package com.example.mvi_sample.ui.loding

import com.google.gson.annotations.SerializedName

data class ServerVersionInfoModel(
    @SerializedName("version") val version : String
)