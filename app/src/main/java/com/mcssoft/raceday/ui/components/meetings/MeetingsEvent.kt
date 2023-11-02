package com.mcssoft.raceday.ui.components.meetings

sealed class MeetingsEvent {
    // TBA
    data class NavToRaces(val id: Long): MeetingsEvent()
}
