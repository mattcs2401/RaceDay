package com.mcssoft.raceday.domain.dto

import com.mcssoft.raceday.domain.model.Runner

data class RunnerDto(
//    val _links: LinksXDto,
    val barrierNumber: Int,
//    val blinkers: Boolean,
//    val claimAmount: Int,
    val dfsFormRating: Int,
//    val earlySpeedRating: Int,
//    val earlySpeedRatingBand: String,
//    val emergency: Boolean,
//    val fixedOdds: FixedOdds,
    val handicapWeight: Double,
//    val harnessHandicap: Any,
    val last5Starts: String?,
//    val parimutuel: Parimutuel,
//    val penalty: Any,
    val riderDriverFullName: String,
    val riderDriverName: String,
    val runnerName: String,
    val runnerNumber: Int,
//    val silkURL: String,
    val tcdwIndicators: String?,
//    val techFormRating: Int,
//    val totalRatingPoints: Int,
    val trainerFullName: String,
    val trainerName: String,
//    val vacantBox: Boolean
)

fun RunnerDto.toRunner(raceId: Long): Runner {
    return Runner(
        raceId = raceId,
        runnerName = runnerName,
        runnerNumber = runnerNumber,
        barrierNumber = barrierNumber,
        tcdwIndicators = tcdwIndicators ?: "",
        last5Starts = last5Starts ?: "",
        riderDriverName = riderDriverName,
        riderDriverFullName = riderDriverFullName,
        trainerName = trainerName,
        trainerFullName = trainerFullName,
        dfsFormRating = dfsFormRating,
        handicapWeight = handicapWeight,
        isChecked = false,                     // not part of Dto.
        isScratched = false                    // not part of Dto.
    )
}