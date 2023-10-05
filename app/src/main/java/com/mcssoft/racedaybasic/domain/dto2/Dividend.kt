package com.mcssoft.racedaybasic.domain.dto2

data class Dividend(
    val countBackLevelDescription: Any,
    val jackpotCarriedOver: Int,
    val poolDividends: List<PoolDividend>,
    val poolStatusCode: String,
    val wageringProduct: String
)