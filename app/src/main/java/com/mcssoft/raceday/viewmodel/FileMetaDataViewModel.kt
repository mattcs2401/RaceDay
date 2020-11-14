package com.mcssoft.raceday.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mcssoft.raceday.database.entity.FileMetaData
import com.mcssoft.raceday.repository.RaceDayRepository
import javax.inject.Inject

class FileMetaDataViewModel @Inject constructor() : ViewModel() {

    @Inject lateinit var raceDayRepo: RaceDayRepository

    private lateinit var fileMetaDataCache: FileMetaData

    private lateinit var allFileMetaDataCache: LiveData<List<FileMetaData>>

    fun getFile(id: Long) = raceDayRepo.getFile(id)

    fun getAllFile() = raceDayRepo.getAllFile()

    fun getCountFileMeta() = raceDayRepo.getCountFileData()
}