package com.mcssoft.raceday.ui.components.runners

import com.mcssoft.raceday.domain.model.Race
import com.mcssoft.raceday.domain.model.Runner

sealed class RunnersEvent {

    data class Check(val race: Race, val runner: Runner): RunnersEvent()
}
