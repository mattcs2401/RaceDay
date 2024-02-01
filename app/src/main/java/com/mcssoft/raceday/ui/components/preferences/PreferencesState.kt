package com.mcssoft.raceday.ui.components.preferences

data class PreferencesState(
    val status: Status,
    var sourceFromApi: Boolean,
) {
    companion object {
        // Flow initializer.
        fun initialise(): PreferencesState {
            return PreferencesState(
                status = Status.Initialise,
                sourceFromApi = true
            )
        }
    }

    sealed class Status {
        data object Initialise : Status()
    }
}

