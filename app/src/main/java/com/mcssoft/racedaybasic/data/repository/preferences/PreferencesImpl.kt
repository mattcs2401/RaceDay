package com.mcssoft.racedaybasic.data.repository.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mcssoft.racedaybasic.R
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
//    private val fromDbKey =
//        booleanPreferencesKey(context.resources.getString(R.string.pref_from_db_key))
//    private val onlyAuNzKey =
//        booleanPreferencesKey(context.resources.getString(R.string.pref_only_aunz_key))
    private val meetingIdKey =
        longPreferencesKey(context.resources.getString(R.string.setting_meeting_id_key))

    /**
     *
     */
    override suspend fun getPreference(pref: Preference): Any {
        return when (pref) {
//            is Preference.FromDbPref -> {
//                getFromDbPref()
//            }
//            is Preference.OnlyAuNzPref -> {
//                getOnlyAuNzPref()
//            }
            is Preference.MeetingIdPref -> {
                getMeetingId()
            }
            else -> {}
        }
    }

    /**
     *
     */
    override suspend fun setPreference(pref: Preference, value: Any) {
        when (pref) {
//            is Preference.FromDbPref -> {
//                setFromDbPref(value as Boolean)
//            }
//            is Preference.OnlyAuNzPref -> {
//                setOnlyAuNzPref(value as Boolean)
//            }
            is Preference.MeetingIdPref -> {
                setMeetingId(value as Long)
            }
            else -> {}
        }
    }


    //<editor-fold default state="collapsed" desc="Region: User selectable preferences">
//    /**
//     * Get the "FromDb" preference.
//     */
//    private suspend fun getFromDbPref(): Boolean {
//        return dsPrefs.data.first()[fromDbKey] ?: false
//    }
//
//    /**
//     * Set the "FromDb" preference.
//     * @param value: The value to set.
//     */
//    private suspend fun setFromDbPref(value: Boolean) {
//        dsPrefs.edit { preferences ->
//            preferences[fromDbKey] = value
//        }
//    }
//
//    /**
//     * Get the "OnlyAuNz" preference.
//     */
//    private suspend fun getOnlyAuNzPref(): Boolean {
//        return dsPrefs.data.first()[onlyAuNzKey] ?: false
//    }
//
//    /**
//     * Set the "OnlyAuNz" preference.
//     * @param value: The value to set.
//     */
//    private suspend fun setOnlyAuNzPref(value: Boolean) {
//        dsPrefs.edit { preferences ->
//            preferences[onlyAuNzKey] = value
//        }
//    }
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: App settings">
    private suspend fun setMeetingId(value: Long) {
        dsPrefs.edit { preferences ->
            preferences[meetingIdKey] = value
        }
    }

    private suspend fun getMeetingId(): Long {
        return dsPrefs.data.first()[meetingIdKey] ?: -1
    }
    //</editor-fold>
}
