package com.mcssoft.raceday.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mcssoft.raceday.database.dao.IFileDataDAO
import com.mcssoft.raceday.database.entity.FileMetaData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class FileMetaRepo @Inject constructor(private val iFileDataDAO: IFileDataDAO) {

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
            fmdList = iFileDataDAO.getFileData()
        }

        ldList.value = fmdList
        return ldList
    }

    fun getFile(id: Long) = iFileDataDAO.getFileData(id)

    fun getCountFileData(): Int {
        var count = -1
        coroutineScope.launch {
            count = iFileDataDAO.getCount()
        }
        return count
    }

    fun hasFileData(): Boolean {
        var value = false
        if (getCountFileData() > 0) {
            value = true
        }
        return value
    }

    fun deleteAllFileData() {
        coroutineScope.launch {
            iFileDataDAO.deleteAll()
        }
    }
}
//</editor-fold>
