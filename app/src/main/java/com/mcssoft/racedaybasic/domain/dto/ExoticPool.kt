package com.mcssoft.racedaybasic.domain.dto

data class ExoticPool(
    val cashOutEligibility: String,
    val legs: List<Leg>,
    val multiLegApproximatesAvailable: Boolean,
    val nextRaceToJump: NextRaceToJump,
    val poolStatusCode: String,
    val startTime: String,
    val wageringProduct: String
)