package com.mcssoft.racedaybasic.data.repository.preferences

interface IPreferences {

    suspend fun getPreference(pref: Preference): Any

    suspend fun setPreference(pref: Preference, value: Any)
}