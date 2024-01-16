package com.mcssoft.raceday.domain.usecase

import com.mcssoft.raceday.domain.usecase.api.SetupBaseFromApi
import com.mcssoft.raceday.domain.usecase.api.SetupRunnersFromApi
import com.mcssoft.raceday.domain.usecase.local.SetupBaseFromLocal
import com.mcssoft.raceday.domain.usecase.meetings.GetMeeting
import com.mcssoft.raceday.domain.usecase.meetings.GetMeetings
import com.mcssoft.raceday.domain.usecase.races.GetRace
import com.mcssoft.raceday.domain.usecase.races.GetRaces
import com.mcssoft.raceday.domain.usecase.runners.GetRunner
import com.mcssoft.raceday.domain.usecase.runners.GetRunners
import com.mcssoft.raceday.domain.usecase.runners.SetRunnerChecked
import com.mcssoft.raceday.domain.usecase.summary.GetSummaries

data class UseCases(

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

    // Get a list of the Runners from the database.
    val getRunner: GetRunner,

    // Check/uncheck the "checked" metadata element on the Runner record.
    val setRunnerChecked: SetRunnerChecked,

    // Get the Summary.
    val getSummaries: GetSummaries,

    )