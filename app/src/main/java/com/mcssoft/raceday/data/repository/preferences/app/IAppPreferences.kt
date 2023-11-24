package com.mcssoft.raceday.data.repository.preferences.app

interface IAppPreferences {

    suspend fun getPreference(pref: AppPreference): Any

    suspend fun setPreference(pref: AppPreference, value: Any)
}