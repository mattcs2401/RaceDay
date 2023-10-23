package com.mcssoft.racedaybasic.domain.usecase

import com.mcssoft.racedaybasic.domain.usecase.api.SetupBaseFromApi
import com.mcssoft.racedaybasic.domain.usecase.api.SetupRunnersFromApi
import com.mcssoft.racedaybasic.domain.usecase.local.SetupBaseFromLocal
import com.mcssoft.racedaybasic.domain.usecase.meetings.GetMeeting
import com.mcssoft.racedaybasic.domain.usecase.meetings.GetMeetings
import com.mcssoft.racedaybasic.domain.usecase.preferences.GetPreferences
import com.mcssoft.racedaybasic.domain.usecase.preferences.SavePreferences
import com.mcssoft.racedaybasic.domain.usecase.races.GetRace
import com.mcssoft.racedaybasic.domain.usecase.races.GetRaces
import com.mcssoft.racedaybasic.domain.usecase.runners.GetRunners
import com.mcssoft.racedaybasic.domain.usecase.runners.SetRunnerChecked
import com.mcssoft.racedaybasic.domain.usecase.summary.GetSummaries
import com.mcssoft.racedaybasic.domain.usecase.summary.SetForSummary

data class RaceDayUseCases(

    // Get and save the base set of data from the Api (Meetings and Races).
    val setupBaseFromApi: SetupBaseFromApi,

    // Get the data from local as an alternative to hitting the Api all the time from the Splash.
    val setupBaseFromLocal: SetupBaseFromLocal,

    // Get and save the base set of data from the Api (Meetings and Races).
    val setupRunnersFromApi: SetupRunnersFromApi,

    // Get a list of Meetings from the database.
    val getMeetings: GetMeetings,

    // Get a single Meeting from the database.
    val getMeeting: GetMeeting,

    // Get a list of the Races from the database.
    val getRaces: GetRaces,

    // Get a Race from the database.
    val getRace: GetRace,

    // Get a list of the Runners from the database.
    val getRunners: GetRunners,

    // Check/uncheck the "checked" metadata element on the Runner record.
    val setRunnerChecked: SetRunnerChecked,

    // Get the Summary.
    val getSummaries: GetSummaries,

    // Update the summary.
    val setForSummary: SetForSummary,

    // Get internal to app preferences.
    val getPreferences: GetPreferences,

    // Save internal to app preferences.
    val savePreferences: SavePreferences,

//    // A quick check that Trainer/Horse tables are (pre)populated.
//    val checkPrePopulate: CheckPrePopulate,
//
//    // Pre-populate Trainer/Horse tables.
//    val prePopulate: PrePopulate
)