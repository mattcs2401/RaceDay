package com.mcssoft.raceday.data.repository.preferences

sealed class Preference {
    // App specific preference, i.e. not user selectable.

    // MeetingId is saved on navigation from Meetings screen to Races screen.
    data object MeetingIdPref: Preference()
    // RaceId is saved on navigation from Races screen to Runners screen.
    data object RaceIdPref: Preference()
}