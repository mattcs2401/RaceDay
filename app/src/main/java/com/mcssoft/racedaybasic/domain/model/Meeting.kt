package com.mcssoft.racedaybasic.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Meeting"
)
data class Meeting(
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0L,

    val location: String,         // e.g. NSW
    val meetingDate: String,      // e.g. 2023-08-21
    val meetingTime: String?,     // TBA - from 1st Race time ?
    val meetingName: String,      // e.g. SCONE
    val prizeMoney: String,       // e.g. $xxx
    val raceType: String,         // e.g. R
    val railPosition: String?,    // e.g. True
    val trackCondition: String?,  // e.g. SOFT6
    val venueMnemonic: String,    // e.g. SCO (Scone)
    val weatherCondition: String?,// e.g. FINE
    val racesNo: Int,             // number of associated Races.
    val sellCode: String,         // e.g. concat of {"meetingCode":"B","scheduledType":"R"}
    val meetingId: String         // e.g. concat of 'venueMnemonic:meetingDate'.
)
/*
DTO class:
data class MeetingDto(
    val _links: Links,
    val displayMeetingName: String,
    val exoticPools: List<ExoticPool>,
    val fixedOddsOnly: Boolean,
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
