package com.mcssoft.raceday.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// TODO - RaceDayDetails table.

@Entity(tableName = "race_day_details")
data class RaceDayDetails(@ColumnInfo(name = "RaceDayDate") var raceDayDate: String) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long? = null    // value inserted by Room.

}