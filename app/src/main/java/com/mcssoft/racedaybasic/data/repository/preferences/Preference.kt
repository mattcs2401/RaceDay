package com.mcssoft.racedaybasic.data.repository.preferences

sealed class Preference {

    data object FromDbPref : Preference()

    data object OnlyAuNzPref : Preference()

    // App specific preference, i.e. not user selectable. MeetingId is saved on navigation from Race
    // to Runner (for when back nav to Race).
    data object MeetingIdPref : Preference()
}