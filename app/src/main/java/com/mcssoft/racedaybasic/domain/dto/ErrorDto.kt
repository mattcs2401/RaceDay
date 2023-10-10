package com.mcssoft.racedaybasic.domain.dto

data class ErrorDto(
    val code: String,
    val details: List<DetailDto>,
    val message: String
)