package com.mcssoft.raceday.data.repository.preferences.app

import kotlinx.serialization.Serializable

@Serializable
data class AppPreferences(
    val meetingId: Long = 0,
    val raceId: Long = 0
)
