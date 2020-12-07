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

class RaceDayPreferences @Inject constructor (context: Context) {

    private val completableJob = Job()

    private val coroutineScope =
        CoroutineScope(Dispatchers.IO + completableJob)

    private val dataStore = context.createDataStore("preferences")

    private val FILE_ID = preferencesKey<Long>("FILE_ID")
    private val FILE_DATE = preferencesKey<String>("FILE_DATE")
    private val FILE_USE = preferencesKey<Boolean>("FILE_USE")

    var fileId: Long = Constants.MINUS_ONE_L
    var fileDate: String = ""
    var fileUse: Boolean = false

    val fileUseAsFlow: Flow<Boolean> = dataStore.data.map {
        val value = it[FILE_USE] ?: false
        value
    }

    val fileDateAsFlow: Flow<String> = dataStore.data.map {
        val value = it[FILE_DATE] ?: ""
        value
    }

    val fileIdAsFlow: Flow<Long> = dataStore.data.map {
        val value = it[FILE_ID] ?: 0
        value
    }

    @JvmName("setFileUse1")
    fun setFileUse(fUse: Boolean) {
        coroutineScope.launch(Dispatchers.IO) {
            storeFileUse(fUse)
        }
    }

    @JvmName("setFileDate1")
    fun setFileDate(fDate: String) {
        coroutineScope.launch(Dispatchers.IO) {
            storeFileDate(fDate)
        }
    }

    @JvmName("setFileId1")
    fun setFileId(fId: Long) {
        coroutineScope.launch(Dispatchers.IO) {
            storeFileId(fId)
        }
    }

    private suspend fun storeFileUse(fUse: Boolean) {
        dataStore.edit {
            it[FILE_USE] = fUse
        }
        fileUse = fUse
    }

    private suspend fun storeFileDate(fDate: String) {
        dataStore.edit {
            it[FILE_DATE] = fDate
        }
        fileDate = fDate
    }

    //Store user data
    private suspend fun storeFileId(fId: Long) {
        dataStore.edit {
            it[FILE_ID] = fId
        }
        fileId = fId
    }

}