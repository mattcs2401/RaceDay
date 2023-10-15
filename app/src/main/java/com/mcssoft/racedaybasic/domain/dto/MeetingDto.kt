package com.mcssoft.racedaybasic.domain.dto

import com.mcssoft.racedaybasic.domain.model.Meeting

data class MeetingDto(
    val displayMeetingName: String,
    val meetingName: String,
    val location: String,
    val raceType: String,
    val meetingDate: String,
    val prizeMoney: String?,
    val weatherCondition: String,
    val trackCondition: String,
    val railPosition: String,
    val venueMnemonic: String?,
    val races: List<RaceDto>,
    val sellCode: SellCodeDto?     // TBA - contains e.g. {"meetingCode":"B","scheduledType":"R"}
)

fun MeetingDto.toMeeting(): Meeting {
    return Meeting(
        location = location,                          // QLD
        meetingDate = meetingDate,                    // e.g. 2023-08-23
        meetingTime = "",                             // TBA - 1st Race time ?
        meetingName = meetingName,                    // e.g. Sunshine Coast
        displayName = displayMeetingName,             // e.g.
        prizeMoney = prizeMoney,                      // TBA.
        raceType = raceType,                          // e.g. R
        railPosition = railPosition,                  // True
        trackCondition = trackCondition,              // e.g. Good4
        venueMnemonic = venueMnemonic,                // e.g SSC
        weatherCondition = weatherCondition,          // Fine
        racesNo = races.size,                         // the number of associated Races.
        meetingId = "$meetingDate:$venueMnemonic",    // TBA - no actual id value in Dto.
        sellCode = "${sellCode?.meetingCode}${sellCode?.scheduledType}"
    )
}