package com.mcssoft.raceday.domain.dto

import com.mcssoft.raceday.domain.model.Trainer

class TrainerDto(
    var raceId: Long,
    var runnerId: Long,
    var sellCode: String,              //
    var venueMnemonic: String,         // venue code.
    var raceNumber: Int,
    var raceTime: String,
    var runnerName: String,
    var runnerNumber: Int,
    var riderDriverName: String,
    var trainerName: String
)

fun TrainerDto.toTrainer(): Trainer {
    return Trainer(
        raceId = raceId,
        runnerId = runnerId,
        sellCode = sellCode,              //
        venueMnemonic = venueMnemonic,         // venue code.
        raceNumber = raceNumber,
        raceTime = raceTime,
        runnerName = runnerName,
        runnerNumber = runnerNumber,
        riderDriverName = riderDriverName,
        trainerName = trainerName
    )
}