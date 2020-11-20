package com.mcssoft.raceday.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.mcssoft.raceday.utility.Constants

class RaceDayPreferences @Inject constructor (private val context: Context) {

    private val completableJob = Job()

    private val coroutineScope =
        CoroutineScope(Dispatchers.IO + completableJob)

    private val dataStore = context.createDataStore("preferences")

    private val FILE_ID_KEY = preferencesKey<Long>("FILE_ID")

    val fileIdFlow: Flow<Long> = dataStore.data.map {
        val value = it[FILE_ID_KEY] ?: 0
        value
    }

    fun setFileId(id: Long) {
        coroutineScope.launch {
            storeFileId(id)
        }
    }

    //Store user data
    private suspend fun storeFileId(id: Long) {
        dataStore.edit {
            it[FILE_ID_KEY] = id
        }
    }

}