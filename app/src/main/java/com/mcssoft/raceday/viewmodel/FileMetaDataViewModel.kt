package com.mcssoft.raceday.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mcssoft.raceday.database.entity.FileMetaData
import com.mcssoft.raceday.repository.FileMetaRepo
import javax.inject.Inject

class FileMetaDataViewModel @Inject constructor(val fileMetaRepo: FileMetaRepo) : ViewModel() {

//    @Inject lateinit var fileMetaRepo: FileMetaRepo

    private lateinit var fileMetaDataCache: FileMetaData

    private lateinit var allFileMetaDataCache: LiveData<List<FileMetaData>>

    fun getFile(id: Long) = fileMetaRepo.getFile(id)

    fun getAllFile() = fileMetaRepo.getAllFile()

    fun getCountFileMeta() = fileMetaRepo.getCountFileData()
}