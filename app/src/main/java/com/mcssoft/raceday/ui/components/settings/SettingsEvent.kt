package com.mcssoft.raceday.ui.components.settings

sealed class SettingsEvent {

    data class Checked(val checked: Boolean, val type: EventType): SettingsEvent()  //: SettingsEvent()

    sealed class EventType {
        data object SOURCEFROMAPI: EventType()   // source the application's data from the Api.
        data object AUTOADDTRAINER: EventType () // auto add Trainers and update the Summary.
    }
}
