package com.example.mvi_sample.ui.loding


sealed class LodingResult {
    data class Success(val version : String): LodingResult()
    data class Failure(val error: Throwable): LodingResult()
    object InFlight : LodingResult()
}