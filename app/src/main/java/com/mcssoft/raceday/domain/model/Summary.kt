package com.mcssoft.raceday.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Summary",
    indices = [
        Index(
            value = ["raceId", "runnerId"]
        )
    ],
)
data class Summary(
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0L,

    var raceId: Long,
    var runnerId: Long,

    var sellCode: String,
    var venueMnemonic: String,
    var raceNumber: Int,
    var raceStartTime: String,
    var runnerNumber: Int,
    var runnerName: String,
    var riderDriverName: String,
    var trainerName: String,

    // Meta data.
    var isPastRaceTime: Boolean,
    var isNotified: Boolean,
    var isWagered: Boolean // TBA - A bet/wager has been made.
)
