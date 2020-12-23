package com.mcssoft.raceday.repository

import android.content.Context
import javax.inject.Inject

class RaceDayPreferences @Inject constructor (context: Context) {
/* https://developer.android.com/training/data-storage/shared-preferences
*
* Notes:
* "apply() changes the in-memory SharedPreferences object immediately but writes the updates to disk
* asynchronously. Alternatively, you can use commit() to write the data to disk synchronously.
* But because commit() is synchronous, you should avoid calling it from your main thread because
* it could pause your UI rendering."
*/

//    private val completableJob = Job()
//    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)

    private val preferences =
            context.getSharedPreferences("raceday_preferences", Context.MODE_PRIVATE)

    //<editor-fold default state="collapsed" desc="Region: User selectable preferences">
    fun setFileUse(value: Boolean) {
        with(preferences.edit()) {
            putBoolean("key_file_use", value)
            apply()
        }
    }

    fun getUseFile(): Boolean {
        return preferences.getBoolean("key_file_use", false)
    }

    fun setDefaultCodeR(value: Boolean) {
        with(preferences.edit()) {
            putBoolean("key_default_code_R", value)
            apply()
        }
    }

    fun getDefaultCodeR(): Boolean {
        return preferences.getBoolean("key_default_code_R", false)
    }

    fun setDefaultCodeT(value: Boolean) {
        with(preferences.edit()) {
            putBoolean("key_default_code_T", value)
            apply()
        }
    }

    fun getDefaultCodeT(): Boolean {
        return preferences.getBoolean("key_default_code_T", false)
    }

    fun setDefaultCodeG(value: Boolean) {
        with(preferences.edit()) {
            putBoolean("key_default_code_G", value)
            apply()
        }
    }

    fun getDefaultCodeG(): Boolean {
        return preferences.getBoolean("key_default_code_G", false)
    }
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: Application preferences">
    fun setFileId(value: Long) {
        with(preferences.edit()) {
            putLong("key_file_id", value)
            apply()
        }
    }

    fun setFileDate(value: String) {
        with(preferences.edit()) {
            putString("key_file_date", value)
            apply()
        }
    }

    fun setDefaultRaceCodes() {
        with(preferences.edit()) {
            val codeSet = mutableListOf<String>("","","")

            if(getDefaultCodeR()) codeSet[0] = "1"
            else codeSet[0] = "0"

            if(getDefaultCodeT()) codeSet[1] = "1"
            else codeSet[1] = "0"

            if(getDefaultCodeG()) codeSet[2] = "1"
            else codeSet[2] = "0"

            putStringSet("key_default_race_codes", codeSet.toSet())

            apply()
        }
    }

    fun getDefaultRaceCodes(): MutableSet<String>? {
        return preferences.getStringSet("key_default_race_codes", mutableListOf<String>("","","").toSet())
    }
    //</editor-fold>

}