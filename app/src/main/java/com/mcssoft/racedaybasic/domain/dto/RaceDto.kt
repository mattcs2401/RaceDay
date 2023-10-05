package com.mcssoft.racedaybasic.domain.dto

import com.mcssoft.racedaybasic.domain.model.Race

data class RaceDto(
    val _links: LinksX?,
    val allIn: Boolean,
    val allowBundle: Boolean,
    val broadcastChannel: String,
    val broadcastChannels: List<String>,
    val cashOutEligibility: String,
    val fixedOddsOnlineBetting: Boolean,
    val hasFixedOdds: Boolean,
    val hasParimutuel: Boolean,
    val matchName: String,
    val meetingDate: String,
    val multiLegApproximates: List<MultiLegApproximate>,
    val raceClassConditions: String,
    val raceDistance: Int,
    val raceName: String,
    val raceNumber: Int,
    val raceStartTime: String,
    val raceStatus: String,
    val results: List<List<Int>>,
    val scratchings: List<Scratching>,
    val skyRacing: SkyRacing,
    val willHaveFixedOdds: Boolean
)

fun RaceDto.toRace(mId: Long): Race {
    return Race(
        mtgId = mId,
        raceNo = raceNumber,
        raceClassConditions = raceClassConditions,
        raceName = raceName,
        raceStartTime = raceStartTime,
        raceDistance = raceDistance,
        raceStatus = raceStatus,
        runnersBaseUrl = _links?.self
    )
}