package com.mcssoft.racedaybasic.ui.components.meetings

sealed class MeetingsEvent {
    // TBA
    data class NavToRaces(val id: Long): MeetingsEvent()
}
