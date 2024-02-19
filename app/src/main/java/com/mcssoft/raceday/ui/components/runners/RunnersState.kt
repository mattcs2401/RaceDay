package com.mcssoft.raceday.ui.components.runners

import com.mcssoft.raceday.domain.model.Race
import com.mcssoft.raceday.domain.model.Runner

data class RunnersState(
    val exception: Exception?,
    val status: Status,
    val lRunners: List<Runner>?,
    val race: Race?,
    var raceId: Long,
    val checked: Boolean
) {

    companion object {
        fun initialise(): RunnersState {
            return RunnersState(
                exception = null,
                status = Status.Initialise,
                lRunners = emptyList(),
                race = null,
                raceId = 0,
                checked = false
            )
        }

    }

    sealed class Status {
        data object Initialise : Status()
        data object Success : Status()
        data object Failure : Status()
    }

    val runners: List<Runner>
        get() = lRunners ?: emptyList()
}
