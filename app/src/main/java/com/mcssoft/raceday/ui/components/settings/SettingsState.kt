package com.mcssoft.raceday.ui.components.settings

data class SettingsState(
    val status: Status,
    var sourceFromApi: Boolean,
    var useNotifications: Boolean
) {
    companion object {
        // Flow initializer.
        fun initialise(): SettingsState {
            return SettingsState(
                status = Status.Initialise,
                sourceFromApi = true,
                useNotifications = true
            )
        }
    }

    sealed class Status {
        data object Initialise : Status()
    }
}

