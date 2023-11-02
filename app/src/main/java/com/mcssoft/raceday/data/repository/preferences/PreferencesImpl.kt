package com.mcssoft.raceday.data.repository.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mcssoft.raceday.R
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Class to set app preferences in a datastore.
 */
class PreferencesImpl @Inject constructor(context: Context) : IPreferences {

    private val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(name = "settings")

    private var dsPrefs: DataStore<Preferences> = context.dataStore

    // Keys for get/set preferences.
    private val meetingIdKey =
        longPreferencesKey(context.resources.getString(R.string.setting_meeting_id_key))
    private val raceIdKey =
        longPreferencesKey(context.resources.getString(R.string.setting_race_id_key))

    /**
     *
     */
    override suspend fun getPreference(pref: Preference): Any {
        return when (pref) {
            is Preference.MeetingIdPref -> {
                getMeetingId()
            }
            is Preference.RaceIdPref -> {
                getRaceId()
            }
        }
    }

    /**
     *
     */
    override suspend fun setPreference(pref: Preference, value: Any) {
        when (pref) {
            is Preference.MeetingIdPref -> {
                setMeetingId(value as Long)
            }
            is Preference.RaceIdPref -> {
                setRaceId(value as Long)
            }
        }
    }


    //<editor-fold default state="collapsed" desc="Region: App settings">
    private suspend fun setMeetingId(value: Long) {
        dsPrefs.edit { preferences ->
            preferences[meetingIdKey] = value
        }
    }

    private suspend fun setRaceId(value: Long) {
        dsPrefs.edit { preferences ->
            preferences[raceIdKey] = value
        }
    }

    private suspend fun getMeetingId(): Long {
        return dsPrefs.data.first()[meetingIdKey] ?: -1
    }

    private suspend fun getRaceId(): Long {
        return dsPrefs.data.first()[raceIdKey] ?: -1
    }
    //</editor-fold>
}
