package com.mcssoft.raceday.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Scratching",
    indices = [
        Index(
            value = ["venueMnemonic", "raceNumber"]
        )
    ],
)
data class Scratching(
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0L,

    val bettingStatus: String,
    val runnerName: String,
    val runnerNumber: Int,

    var venueMnemonic: String,
    var raceNumber: Int
)
