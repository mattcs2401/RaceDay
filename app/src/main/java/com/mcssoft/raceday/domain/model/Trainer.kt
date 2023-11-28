package com.mcssoft.raceday.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * A type of summary record that gives basic details about the race, runner and associated trainer.
 */
@Entity(
    tableName = "Trainer",
    indices = [Index(
        value = ["raceId", "runnerId"]
    )],
)
data class Trainer(
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0L,

    var raceId: Long,        // not displayed.
    var runnerId: Long,      // not displayed.

    var sellCode: String,              //
    var venueMnemonic: String,         // venue code.
    var raceNumber: Int,
    var raceTime: String,
    var runnerName: String,
    var runnerNumber: Int,
    var riderDriverName: String,
    var trainerName: String

)