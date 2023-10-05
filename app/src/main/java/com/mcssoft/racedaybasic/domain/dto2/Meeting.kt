package com.mcssoft.racedaybasic.domain.dto2

data class Meeting(
    val location: String,
    val meetingDate: String,
    val meetingName: String,
    val raceType: String,
    val sellCode: SellCode,
    val trackCondition: String,
    val venueMnemonic: String,
    val weatherCondition: String
)