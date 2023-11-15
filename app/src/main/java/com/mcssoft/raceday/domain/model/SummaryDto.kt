package com.mcssoft.raceday.domain.model

/**
 * Workaround to create a Summary object, but without the Summary._id value as that's not known
 * before insert.
 */
data class SummaryDto(
    var raceId: Long,             // foreign key.
    var runnerId: Long,           // "       "

    var sellCode: String,         // e.g. BR (from Race).
    var venueMnemonic: String,    // e.g. DBM (from Race).
    var raceNumber: Int,          // e.g. 1 (from Race).
    var raceStartTime: String,    // e.g. 12:30 (from Race).
    var runnerNumber: Int,        // e.g. 2 (from Runner).
    var runnerName: String,        // e.g. "name" (from Runner).
)

fun SummaryDto.toSummary(): Summary {
    return Summary(
        raceId = raceId,
        runnerId = runnerId,
        sellCode = sellCode,
        venueMnemonic = venueMnemonic,
        raceNumber = raceNumber,
        raceStartTime = raceStartTime,
        runnerNumber = runnerNumber,
        runnerName = runnerName
    )
}
