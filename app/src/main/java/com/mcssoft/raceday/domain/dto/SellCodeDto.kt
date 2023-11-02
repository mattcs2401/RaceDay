package com.mcssoft.raceday.domain.dto

data class SellCodeDto(
    val meetingCode: String,      // e.g. "B"
    val scheduledType: String     // e.g. "R"
)