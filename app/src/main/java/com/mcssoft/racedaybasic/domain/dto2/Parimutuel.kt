package com.mcssoft.racedaybasic.domain.dto2

data class Parimutuel(
    val bettingStatus: String,
    val isFavouriteExact2: Boolean,
    val isFavouritePlace: Boolean,
    val isFavouriteWin: Boolean,
    val marketMovers: List<MarketMover>,
    val percentageChange: Int,
    val returnExact2: Int,
    val returnPlace: Double,
    val returnWin: Double
)