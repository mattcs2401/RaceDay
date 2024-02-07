package com.mcssoft.raceday.domain.usecase

import com.mcssoft.raceday.domain.usecase.api.SetupBaseFromApi
import com.mcssoft.raceday.domain.usecase.api.SetupRunnersFromApi

data class UseCases(

    // Get and save the base set of data from the Api (Meetings and Races).
    val setupBaseFromApi: SetupBaseFromApi,

    // Get and save the base set of data from the Api (Meetings and Races).
    val setupRunnersFromApi: SetupRunnersFromApi,

)
