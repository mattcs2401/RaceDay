package com.mcssoft.raceday.ui.components.meetings

sealed class MeetingEvent {

//    // Get the Summary listing (again).
//    data object Refresh : MeetingEvent()

    // Remove a Summary item from the listing.
    data class RefreshMeeting(val meetingId: Long) : MeetingEvent()
}
