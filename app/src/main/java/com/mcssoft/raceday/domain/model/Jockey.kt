package com.mcssoft.raceday.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * A type of summary record that gives basic details about the race, runner and associated jockey.
 */
@Entity(
    tableName = "Jockey",
//    indices = [Index(
//        value = ["raceId", "runnerId"]
//    )],
)
data class Jockey(
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0L,

//    var raceId: Long,             //
//    var runnerId: Long,           //
//
//    var sellCode: String,         // e.g. BR (from Race).
//    var venueMnemonic: String,    // e.g. DBN (from Race).
//    var raceNumber: Int,          // e.g. 1 (from Race).
//    var raceStartTime: String,    // e.g. 12:30 (from Race).
//    var runnerNumber: Int,        // e.g. 2 (from Runner).
//    var runnerName: String,        // e.g. "name" (from Runner).

)