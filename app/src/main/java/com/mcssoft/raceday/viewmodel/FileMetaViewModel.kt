package com.mcssoft.raceday.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mcssoft.raceday.repository.ProtoRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class FileMetaViewModel @Inject constructor(private val context: Context) : ViewModel() {

    @Inject lateinit var protoRepo: ProtoRepository

//    val xyz = protoRepo.read.asLiveData()
}