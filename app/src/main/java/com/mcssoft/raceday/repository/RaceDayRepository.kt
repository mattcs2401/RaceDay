package com.mcssoft.raceday.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mcssoft.raceday.database.dao.IFileDataDAO
import com.mcssoft.raceday.database.dao.IRaceDayDAO
import com.mcssoft.raceday.database.entity.FileMetaData
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RaceDayRepository @Inject constructor(private val iRaceDayDAO: IRaceDayDAO,
                                            private val iFileDataDAO: IFileDataDAO) {

    //<editor-fold default state="collapsed" desc="Region: Coroutine">
    val completableJob = Job()

    private val coroutineScope =
        CoroutineScope(Dispatchers.IO + completableJob)
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: File data">
    fun getAllFile(): LiveData<List<FileMetaData>> {
        var fmdList = listOf<FileMetaData>()
        val ldList = MutableLiveData<List<FileMetaData>>()

        coroutineScope.launch {
            fmdList =  iFileDataDAO.getFileData()
        }

        ldList.value = fmdList
        return ldList
    }

    fun getFile(id: Long) = iFileDataDAO.getFileData(id)

    fun getCount(): Int {
        var count = -1
        coroutineScope.launch {
            count = iFileDataDAO.getCount()
        }
        return count
    }

    fun hasFileData(): Boolean {
        var value = false
        if(getCount() > 0) {
            value = true
        }
        return value
    }
    //</editor-fold>



}
/*
    val errorHandler = CoroutineExceptionHandler { _, exception ->
      AlertDialog.Builder(this).setTitle("Error")
              .setMessage(exception.message)
              .setPositiveButton(android.R.string.ok) { _, _ -> }
              .setIcon(android.R.drawable.ic_dialog_alert).show()
    }
 */