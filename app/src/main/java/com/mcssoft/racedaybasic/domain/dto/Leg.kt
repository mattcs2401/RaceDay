package com.mcssoft.racedaybasic.domain.dto

data class Leg(
    val legNumber: Int,
    val raceNumber: Int,
    val raceType: String,
    val startTime: String,
    val venueMnemonic: String
)