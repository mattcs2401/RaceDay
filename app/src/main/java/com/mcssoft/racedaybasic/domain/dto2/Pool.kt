package com.mcssoft.racedaybasic.domain.dto2

data class Pool(
    val _links: Links,
    val cashOutEligibility: String,
    val cominglingGuests: Boolean,
    val cominglingHost: String,
    val cominglingHostName: String,
    val jackpot: Double,
    val legNumber: Int,
    val legs: List<Leg>,
    val poolHistory: List<PoolHistory>,
    val poolStatusCode: String,
    val poolTotal: Double,
    val substitute: Int,
    val wageringProduct: String
)