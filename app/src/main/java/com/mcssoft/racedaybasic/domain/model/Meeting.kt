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
    val meetingName: String,      // e.g. SCONE
    val prizeMoney: String,       // e.g. $xxx
    val raceType: String,         // e.g. R
    val railPosition: String?,    // e.g. True
    val trackCondition: String?,  // e.g. SOFT6
    val venueMnemonic: String,    // e.g. SCO (Scone)
    val weatherCondition: String?, // e.g. FINE
    val racesNo: Int
) {
    var meetingId: String = "$venueMnemonic:$meetingDate"       // TBA - no actual id value  in Dto.
//    val races: List<RaceDto>      // Races listing.
//    val sellCode: SellCodeDto,       // TBA
}
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
    val races: List<RaceDto>,        // Races listing.
    val railPosition: String,     // e.g. True
    val sellCode: SellCodeDto,       // TBA
    val trackCondition: String,   // e.g. SOFT6
    val venueMnemonic: String,    // e.g. SCO (Scone)
    val weatherCondition: String  // e.g. FINE
)
 */
