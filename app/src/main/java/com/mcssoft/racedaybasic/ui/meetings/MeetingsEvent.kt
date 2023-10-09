package com.mcssoft.racedaybasic.ui.meetings

sealed class MeetingsEvent {
    // TBA
    data class NavToRaces(val id: Long): MeetingsEvent()
}
