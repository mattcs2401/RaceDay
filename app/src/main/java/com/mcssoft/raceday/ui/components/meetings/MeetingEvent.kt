package com.mcssoft.raceday.ui.components.meetings

sealed class MeetingEvent {

    /**
     * Refresh a particular Meeting from the Api.
     * @param venueMnemonic: The code for the venue.
     */
    data class RefreshMeeting(val venueMnemonic: String) : MeetingEvent()

    /**
     * Save the currently selected Meeting's id to the cache.
     * @param meetingId: The id of the selected Meeting.
     */
    data class SaveMeetingId(val meetingId: Long) : MeetingEvent()
}
