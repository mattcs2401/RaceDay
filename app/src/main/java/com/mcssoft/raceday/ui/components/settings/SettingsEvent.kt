package com.mcssoft.raceday.ui.components.settings

sealed class SettingsEvent {

    data class Checked(val checked: Boolean): SettingsEvent()
}
