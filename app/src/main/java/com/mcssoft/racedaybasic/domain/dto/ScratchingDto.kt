package com.mcssoft.racedaybasic.domain.dto

import com.mcssoft.racedaybasic.domain.model.Scratching

data class ScratchingDto(
    val bettingStatus: String,
    val runnerName: String,
    val runnerNumber: Int
)

fun ScratchingDto.toScratching(rId: Long): Scratching {
    return Scratching(
        rId = rId,                           // _id value of the Race.
        bettingStatus = bettingStatus,
        runnerName = runnerName,
        runnerNumber = runnerNumber
    )
}