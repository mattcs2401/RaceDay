package com.mcssoft.raceday.utility

object Constants {
    // For long strings, e.g. Race names, just take 1st 30 characters.
    const val TAKE = 30
    // For long rail position details in MeetingItemExtra.
    const val RAIL_TAKE = 12
    // Max length of rail position details. For values > than this, then RAIL_TAKE.
    const val RAIL_MAX = 16
    // For long jockey names in RunnerItem.
    const val JOCKEY_TAKE = 10
    // Max length of a jockey name. For values > this then JOCKEY_TAKE.
    const val JOCKEY_MAX = 12

    // The "key" for the parameter passed from the MeetingsScreen to the RacesScreen.
    const val KEY_MEETING_ID: String = "meetingId"

    // The "key" for the ...
    const val KEY_RACE_ID: String = "raceId"

//    // The "key" for the ...
//    const val KEY_FROM_DB_PREF_CHANGE: String = "keyFromDbChange"
//    const val KEY_ONLY_AUNZ_PREF_CHANGE: String = "keyOnlyAuNzChange"

    // Base meeting type. Only Meetings of this type will be displayed (ATT).
    const val MEETING_TYPE: String = "R"    // (R)ace.

    // Base set of Meeting locations.
    val LOCATIONS = listOf("ACT","QLD","NSW","VIC","SA","WA","NT","NZL")

}