package com.example.mvi_sample.ui.loding.state


sealed class LodingResult {
    data class SuccessServerCheck(val version : String): LodingResult()
    data class SuccessGetTempData(val version : String): LodingResult()
    data class Failure(val error: Throwable): LodingResult()
    object InFlight : LodingResult()
}