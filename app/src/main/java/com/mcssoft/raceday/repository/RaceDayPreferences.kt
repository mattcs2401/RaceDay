package com.mcssoft.raceday.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.mcssoft.raceday.utility.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class RaceDayPreferences @Inject constructor (private val context: Context) {

    private val completableJob = Job()

    private val coroutineScope =
        CoroutineScope(Dispatchers.IO + completableJob)

    private val dataStore = context.createDataStore("preferences")

    private val FILE_ID_KEY = preferencesKey<Long>("FILE_ID")

    var fId: Long = Constants.MINUS_ONE_L

    val fileIdFlow: Flow<Long> = dataStore.data.map {
        val value = it[FILE_ID_KEY] ?: 0
        value
    }

    fun setFileId(fileId: Long) {
        coroutineScope.launch(Dispatchers.Default) {
            storeFileId(fileId)
        }
    }

    //Store user data
    private suspend fun storeFileId(fileId: Long) {
        dataStore.edit {
            it[FILE_ID_KEY] = fileId
        }
        fId = fileId
    }

}