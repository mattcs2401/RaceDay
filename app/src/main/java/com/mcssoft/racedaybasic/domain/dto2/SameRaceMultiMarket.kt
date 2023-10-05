package com.mcssoft.racedaybasic.domain.dto2

data class SameRaceMultiMarket(
    val allowMulti: Boolean,
    val betOption: String,
    val bettingStatus: String,
    val id: Int,
    val informationMessage: Any,
    val message: Any,
    val name: String,
    val propositions: List<Proposition>,
    val refundsAndDeductions: Boolean,
    val shortName: String
)