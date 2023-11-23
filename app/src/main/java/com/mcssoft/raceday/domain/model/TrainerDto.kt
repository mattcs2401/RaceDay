package com.mcssoft.raceday.domain.model

class TrainerDto(
    var raceId: Long,
    var runnerName: String,
    var runnerNumber: Int,
    var riderDriverName: String,
    var trainerName: String
)

fun TrainerDto.toTrainer(): Trainer {
    return Trainer(
        raceId = raceId,
        runnerName = runnerName,
        runnerNumber = runnerNumber,
        riderDriverName = riderDriverName,
        trainerName = trainerName
    )
}