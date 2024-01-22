package com.mcssoft.raceday.domain.dto

data class BaseDto(
    val meetings: List<MeetingDto>,
    val error: ErrorDto // TBA for invalid path errorDto (url is malformed http 400).
)
