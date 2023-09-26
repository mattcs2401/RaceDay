package com.mcssoft.racedaybasic.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Runner",
    indices = [Index(value = ["raceId"])],
    foreignKeys = [
        ForeignKey(
            entity = Race::class,
            parentColumns = ["_id"],
            childColumns = ["raceId"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class Runner(
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0L,
    var raceId: Long = 0,

    var checked: Boolean = false
)