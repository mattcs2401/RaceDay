package com.mcssoft.raceday.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Summary(
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0L

)