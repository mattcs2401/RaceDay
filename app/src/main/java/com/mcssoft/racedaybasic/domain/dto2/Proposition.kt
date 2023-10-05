package com.mcssoft.racedaybasic.domain.dto2

data class Proposition(
    val allowPlace: Boolean,
    val bettingStatus: String,
    val name: String,
    val number: Int,
    val placeDeduction: Int,
    val returnPlace: Double,
    val returnWin: Double,
    val runnerNumber: Int,
    val scratchedTime: String,
    val winDeduction: Int
)