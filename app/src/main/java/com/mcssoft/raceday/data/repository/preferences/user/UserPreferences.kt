package com.mcssoft.raceday.data.repository.preferences.user

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val sourceFromApi: Boolean = true,      // source app data from the Api.
    val autoAddTrainers: Boolean = true     // auto update Summary from Trainer info collected.
)
