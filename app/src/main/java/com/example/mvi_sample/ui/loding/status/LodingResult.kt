package com.example.mvi_sample.ui.loding.status


sealed class LodingResult {
    data class SuccessServerCheck(val version : String): LodingResult()
    data class Failure(val error: Throwable): LodingResult()
    object InFlight : LodingResult()
}