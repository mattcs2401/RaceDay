package com.mcssoft.raceday.ui.components.settings

sealed class SettingsEvent {

    data class Checked(
        val checked: Boolean,
        val type: EventType
    ) : SettingsEvent()

    sealed class EventType {
        data object SOURCE_FROM_API : EventType() // source the application's data from the Api.
        data object USE_NOTIFICATIONS : EventType() // use notifications by alarm timer.
    }
}
