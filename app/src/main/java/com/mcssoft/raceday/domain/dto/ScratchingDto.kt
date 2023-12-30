package com.mcssoft.raceday.domain.dto

import com.mcssoft.raceday.domain.model.Scratching

data class ScratchingDto(
    val bettingStatus: String,
    val runnerName: String,
    val runnerNumber: Int
)

fun toScratching(
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
