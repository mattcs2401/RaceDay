package com.mcssoft.raceday.utility

object Constants {
    // Basics (used in calculations).
    const val FIVE = 5
    const val TEN = 10
    const val TWENTY_FIVE = 25.toLong()
    const val THIRTY = 30
    const val SIXTY = 60
    const val THREE_HUNDRED = 300
    const val THOUSAND = 1000 // representing 1000 milli seconds.

    // For long strings, e.g. Race names, just take 1st 30 characters.
    const val TAKE = 30

    // Max length of rail position details. For values > than this, then RAIL_TAKE.
    const val RAIL_MAX = 16
    const val RAIL_TAKE = 12

    // Max length of Trainer Full Name. For values > than this, then TRAINER_TAKE.
    const val TRAINER_MAX = 20
    const val TRAINER_TAKE = 16

    // Max length of a jockey name. For values > this then JOCKEY_TAKE.
    const val JOCKEY_MAX = 12
    const val JOCKEY_TAKE = 10

    // The "key" for the parameter passed from the MeetingsScreen to the RacesScreen.
    const val KEY_MEETING_ID: String = "meetingId"

    // The "key" for the ...
    const val KEY_RACE_ID: String = "raceId"

    const val KEY_RUNNER_ID: String = "runnerId"

    // Base meeting type. Only Meetings of this type will be displayed (ATT).
    const val MEETING_TYPE: String = "R" // (R)ace.

    // Base set of Meeting locations.
    val LOCATIONS = listOf("ACT", "QLD", "NSW", "VIC", "SA", "WA", "NT", "NZL")

    // Alarm values.
    const val ONE_MINUTE = (THOUSAND * SIXTY).toLong()

    const val THIRTY_SECONDS = (THOUSAND * THIRTY).toLong()

    const val FIVE_MINUTES = (THOUSAND * SIXTY * FIVE).toLong()
}
