package com.mcssoft.racedaybasic.domain.dto2

data class FixedOdds(
    val allowPlace: Boolean,
    val bettingStatus: String,
    val differential: Any,
    val flucs: List<Fluc>,
    val isFavouritePlace: Boolean,
    val isFavouriteWin: Boolean,
    val percentageChange: Int,
    val placeDeduction: Int,
    val propositionNumber: Int,
    val returnHistory: List<ReturnHistory>,
    val returnPlace: Double,
    val returnWin: Double,
    val returnWinOpen: Double,
    val returnWinOpenDaily: Double,
    val returnWinTime: String,
    val scratchedTime: String,
    val winDeduction: Int
)