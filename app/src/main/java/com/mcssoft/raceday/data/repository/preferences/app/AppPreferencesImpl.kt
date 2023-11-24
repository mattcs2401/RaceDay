package com.mcssoft.raceday.data.repository.preferences.app

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
 * Class to get/set non-user specific app preferences in a datastore.
 */
class AppPreferencesImpl @Inject constructor(context: Context) : IAppPreferences {

    private val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(name = "settings")

    private var dsPrefs: DataStore<Preferences> = context.dataStore

    // Keys for get/set preferences.
    private val meetingIdKey =
        longPreferencesKey(context.resources.getString(R.string.setting_meeting_id_key))
    private val raceIdKey =
        longPreferencesKey(context.resources.getString(R.string.setting_race_id_key))

    /**
     * Get an app specific Preference.
     * @param pref: The Preference type.
     */
    override suspend fun getPreference(pref: AppPreference): Any {
        return when (pref) {
            is AppPreference.MeetingIdPref -> {
                getMeetingId()
            }
            is AppPreference.RaceIdPref -> {
                getRaceId()
            }
        }
    }

    /**
     * Set an app specific Preference.
     * @param pref: The Preference type.
     * @param value: The Preference value.
     */
    override suspend fun setPreference(pref: AppPreference, value: Any) {
        when (pref) {
            is AppPreference.MeetingIdPref -> {
                setMeetingId(value as Long)
            }
            is AppPreference.RaceIdPref -> {
                setRaceId(value as Long)
            }
        }
    }


    //<editor-fold default state="collapsed" desc="Region: Set/Get app specific values.">
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
