package com.mcssoft.raceday.ui.components.settings

data class SettingsState(
    val status: Status,
    var sourceFromApi: Boolean = true
) {

    companion object {
        // Flow initializer.
        fun initialise(): SettingsState {
            return SettingsState(
                status = Status.Initialise
            )
        }
    }

    sealed class Status {
        data object Initialise : Status()
    }
}

