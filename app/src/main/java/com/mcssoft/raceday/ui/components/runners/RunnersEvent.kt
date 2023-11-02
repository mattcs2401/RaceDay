package com.mcssoft.raceday.ui.components.runners

import com.mcssoft.raceday.domain.model.Runner

sealed class RunnersEvent {

    data class Check(
        val runner: Runner
    ): RunnersEvent()
}
