package com.mcssoft.raceday.ui.components.meetings

sealed class MeetingEvent {

    /**
     * Refresh a particular Meeting from the Api.
     * @param venueMnemonic: The code for the venue.
     */
    data class RefreshMeeting(val venueMnemonic: String) : MeetingEvent()
}
