package com.mcssoft.raceday.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.createDataStore
import androidx.datastore.preferences.createDataStore
import com.mcssoft.raceday.FileMetaData
import com.mcssoft.raceday.utility.RaceDaySerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException
import javax.inject.Inject

class ProtoRepository @Inject constructor (private val context: Context) {

    private val dataStore: DataStore<FileMetaData> = context.createDataStore(
        "file_meta_data",
        RaceDaySerializer()
    )

    val read: Flow<FileMetaData> = dataStore.data
        .catch { ex ->
            if(ex is IOException) {
                Log.d("TAG", ex.message.toString())
                emit(FileMetaData.getDefaultInstance())
            } else {
                throw ex
            }
        }

    suspend fun updateFileId(fileId: Long) {
        dataStore.updateData { pref ->
            pref.toBuilder().setFileId(fileId).build()
        }
    }
}