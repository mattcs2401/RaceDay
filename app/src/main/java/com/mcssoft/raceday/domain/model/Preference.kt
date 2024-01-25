package com.mcssoft.raceday.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Preference",
    indices = [
        Index(
            value = [
                "name", "value"
            ],
            unique = true
        )
    ]
)
data class Preference(
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0L,

    val name: String,
    val value: Boolean,
    val description: String
)
