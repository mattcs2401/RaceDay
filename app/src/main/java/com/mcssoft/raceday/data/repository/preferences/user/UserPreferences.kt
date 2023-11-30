package com.mcssoft.raceday.data.repository.preferences.user

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val sourceFromApi: Boolean = true
)
