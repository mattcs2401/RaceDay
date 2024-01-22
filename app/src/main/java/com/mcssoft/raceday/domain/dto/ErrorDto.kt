package com.mcssoft.raceday.domain.dto

data class ErrorDto(
    val code: String,
    val details: List<DetailDto>,
    val message: String
)
