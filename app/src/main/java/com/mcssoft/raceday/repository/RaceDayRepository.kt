package com.mcssoft.raceday.repository

import android.content.Context
import com.mcssoft.raceday.database.dao.IRaceDayDAO
import kotlinx.coroutines.*
import javax.inject.Inject

class RaceDayRepository @Inject constructor(private val context: Context) {

    companion object {
        val completableJob = Job()

        private val coroutineScope =
                CoroutineScope(Dispatchers.IO + completableJob)
    }

    //<editor-fold default state="collapsed" desc="Region: File data">
//    fun getAllFile(): LiveData<List<FileMetaData>> {
//        var fmdList = listOf<FileMetaData>()
//        val ldList = MutableLiveData<List<FileMetaData>>()
//
//        coroutineScope.launch {
//            fmdList =  iFileDataDAO.getFileData()
//        }
//
//        ldList.value = fmdList
//        return ldList
//    }

//    fun getFile(id: Long) = iFileDataDAO.getFileData(id)

//    fun getCountFileData(): Int {
//        var count = -1
//        coroutineScope.launch {
//            count = iFileDataDAO.getCount()
//        }
//        return count
//    }

//    fun hasFileData(): Boolean {
//        var value = false
//        if(getCountFileData() > 0) {
//            value = true
//        }
//        return value
//    }

//    fun deleteAllFileData() {
//        coroutineScope.launch {
//            iFileDataDAO.deleteAll()
//        }
//    }
    //</editor-fold>



}
/*
FYI
https://vladsonkin.com/android-coroutine-scopes-how-to-handle-a-coroutine/?utm_source=feedly&utm_medium=rss&utm_campaign=android-coroutine-scopes-how-to-handle-a-coroutine
 */
/*
    val errorHandler = CoroutineExceptionHandler { _, exception ->
      AlertDialog.Builder(this).setTitle("Error")
              .setMessage(exception.message)
              .setPositiveButton(android.R.string.ok) { _, _ -> }
              .setIcon(android.R.drawable.ic_dialog_alert).show()
    }
 */