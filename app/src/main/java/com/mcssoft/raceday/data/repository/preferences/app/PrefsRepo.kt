package com.mcssoft.raceday.data.repository.preferences.app

import android.content.SharedPreferences
import javax.inject.Inject

class PrefsRepo @Inject constructor(preferences: SharedPreferences) {

    /**
     * If set, source app data from the Api, else it'll be whatever is already there.
     */
    var fromApi: Boolean by preferences

    /**
     * App specific, store 'id' values instead of passing them as parameters to the relevant screen.
     */
    var meetingId: Long by preferences

    var raceId: Long by preferences

    var runnersId: Long by preferences

    var runnerId: Long by preferences

    var summaryId: Long by preferences

    fun clearIds() {
        meetingId = 0L
        raceId = 0L
        runnersId = 0L
        summaryId = 0L
    }
}