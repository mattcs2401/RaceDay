package com.mcssoft.raceday.data.repository.preferences

import android.content.SharedPreferences
import javax.inject.Inject

class PrefsRepo @Inject constructor(preferences: SharedPreferences) {

    // If set, source app data from the Api, else it'll be whatever is already there.
    var fromApi: Boolean by preferences
}