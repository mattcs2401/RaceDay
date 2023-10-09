package com.mcssoft.racedaybasic.utility

object Constants {
    /*
      Notes: Values in here are largely because they are used where we can't get a Context to use a
             resource. ViewModels are an example.
    */

    // For long strings, e.g. Race names, just take 1st 30 characters.
    const val TAKE = 30

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