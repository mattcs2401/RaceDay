package com.mcssoft.raceday.ui.components.preferences

sealed class PreferencesEvent {

    data class Checked(
        val checked: Boolean,
        val type: EventType
    ) : PreferencesEvent()

    sealed class EventType {
        data object SourceFromApi : EventType() // source the application's data from the Api.
    }
}
