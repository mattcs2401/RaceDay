package com.mcssoft.racedaybasic.domain.dto

import com.mcssoft.racedaybasic.domain.model.Scratching

data class ScratchingDto(
    val bettingStatus: String,
    val runnerName: String,
    val runnerNumber: Int
)

fun ScratchingDto.toScratching(
    venueMnemonic: String,
    raceNumber: Int,
    scratchingDto: ScratchingDto
): Scratching {
    return Scratching(
        venueMnemonic = venueMnemonic,
        raceNumber = raceNumber,
        bettingStatus = scratchingDto.bettingStatus,
        runnerName = scratchingDto.runnerName,
        runnerNumber = scratchingDto.runnerNumber
    )
}
