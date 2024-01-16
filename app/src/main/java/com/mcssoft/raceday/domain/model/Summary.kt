package com.mcssoft.raceday.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Summary",
    indices = [Index(
        value = ["raceId", "runnerId"]
    )],
)
data class Summary(
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0L,

    var raceId: Long,             //
    var runnerId: Long,           //

    var sellCode: String,         // e.g. BR (from Race).
    var venueMnemonic: String,    // e.g. DBN (from Race).
    var raceNumber: Int,          // e.g. 1 (from Race).
    var raceStartTime: String,    // e.g. 12:30 (from Race).
    var runnerNumber: Int,        // e.g. 2 (from Runner).
    var runnerName: String,       // e.g. "name" (from Runner).
    var riderDriverName: String,
    var trainerName: String,

    // Meta data.
    var isPastRaceTime: Boolean,  // raceStartTime is now in the past.
    var isNotified: Boolean,      // Summary has been picked up for notifications.
    var isWagered: Boolean        // TBA - A bet/wager has been made.
)