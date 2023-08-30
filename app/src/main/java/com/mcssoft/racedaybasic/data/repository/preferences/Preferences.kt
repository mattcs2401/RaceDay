package com.mcssoft.racedaybasic.data.repository.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.mcssoft.racedaybasic.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Class to set app preferences in a datastore.
 */
class Preferences @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    context: Context
): IPreferences {

    // Keys for get/set preferences.
    private val fromDbKey =
        booleanPreferencesKey(context.resources.getString(R.string.pref_from_db_key))

    //<editor-fold default state="collapsed" desc="Region: User selectable preferences">
    /**
     * Get the "FromDb" preference.
     */
    override fun getFromDbPref(): Flow<Boolean> {
        return dataStore.data
            .catch {
                emit(emptyPreferences())
            }
            .map { preferences ->
                preferences[fromDbKey] ?: false  // false if value is null.
            }
    }

    /**
     * Set the "FromDb" preference.
     * @param value: The value to set.
     */
    override suspend fun setFromDbPref(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[fromDbKey] = value
        }
    }

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
//    //</editor-fold>

//    //<editor-fold default state="collapsed" desc="Region: App settings">
//    private suspend fun setMeetingId(value: Long) {
//        dsPrefs.edit { preferences ->
//            preferences[meetingIdKey] = value
//        }
//    }
//
//    private suspend fun getMeetingId(): Long {
//        return dsPrefs.data.first()[meetingIdKey] ?: -1
//    }
//    //</editor-fold>
}
