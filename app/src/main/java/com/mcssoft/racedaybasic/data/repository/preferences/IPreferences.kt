package com.mcssoft.racedaybasic.data.repository.preferences

import kotlinx.coroutines.flow.Flow

interface IPreferences {

    fun getFromDbPref(): Flow<Boolean>

    suspend fun setFromDbPref(value: Boolean)

}