package com.mcssoft.raceday.repository

import android.content.Context
import kotlinx.coroutines.*
import javax.inject.Inject

class RaceDayPreferences @Inject constructor (private val context: Context) {
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

    fun setFileUse(value: Boolean) {
        preferences.edit().putBoolean("key_file_use", value).apply()
    }

    fun getFileUse(): Boolean {
        return preferences.getBoolean("key_file_use", false)
    }

    fun setFileId(value: Long) {
        preferences.edit().putLong("key_file_id", value).apply()
    }

    fun setFileDate(value: String) {
        preferences.edit().putString("key_file_date", value).apply()
    }


}