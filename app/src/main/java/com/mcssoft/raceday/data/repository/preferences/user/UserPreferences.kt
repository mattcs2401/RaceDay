package com.mcssoft.raceday.data.repository.preferences.user

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    var sourceFromApi: Boolean = true,      // source app data from the Api.
    var autoAddTrainers: Boolean = true     // auto update Summary from Trainer info collected.
)
