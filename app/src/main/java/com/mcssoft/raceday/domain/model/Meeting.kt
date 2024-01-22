package com.mcssoft.raceday.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Meeting",
    indices = [
        Index(
            value = [
                "meetingDate", "venueMnemonic", "numRaces"
            ],
            unique = true
        )
    ]
)
data class Meeting(
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0L,

    val location: String, // e.g. NSW
    val meetingDate: String, // e.g. 2023-08-21
    val meetingTime: String?, // TBA - from 1st Race time ?
    val meetingName: String, // e.g. SCONE
    val displayName: String?, // e.g. TBA
    val prizeMoney: String?, // e.g. $xxx
    val raceType: String, // e.g. R
    val railPosition: String?, // e.g. True
    val trackCondition: String?, // e.g. SOFT6
    val venueMnemonic: String?, // e.g. SCO (Scone)
    val weatherCondition: String?, // e.g. FINE
    val numRaces: Int, // number of associated Races.
    var sellCode: String?, // e.g. concat of {"meetingCode":"B","scheduledType":"R"}
)

// data class MeetingSubset(
//    @ColumnInfo(name = "meetingDate") val meetingDate: String,
//    @ColumnInfo(name = "venueMnemonic") val venueMnemonic: String,
//    @ColumnInfo(name = "racesNo") val racesNo: Int
// )
/*
DTO class:
data class MeetingDto(
    val displayMeetingName: String,
    val location: String,         // e.g. NSW
    val meetingDate: String,      // e.g. 2023-08-21
    val meetingName: String,      // e.g. SCONE
    val prizeMoney: String,       // e.g. $xxx
    val raceType: String,         // e.g. R
    val races: List<RaceDto>,     // Races listing.
    val railPosition: String,     // e.g. 'True', or 'True all the way' or 'Out 5m entire', etc.
    val sellCode: SellCodeDto,    // e.g. {"meetingCode":"B","scheduledType":"R"}
    val trackCondition: String,   // e.g. SOFT6
    val venueMnemonic: String,    // e.g. SCO (Scone)
    val weatherCondition: String  // e.g. FINE
)
 */
