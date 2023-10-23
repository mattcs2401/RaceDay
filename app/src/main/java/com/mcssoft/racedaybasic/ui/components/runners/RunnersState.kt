package com.mcssoft.racedaybasic.ui.components.runners

import com.mcssoft.racedaybasic.domain.model.Race
import com.mcssoft.racedaybasic.domain.model.Runner

data class RunnersState(
    val exception: Exception?,
    val status: Status,
    val loading: Boolean,
    val runners: List<Runner>,
    val race: Race?,
    val raceId: Long
    ) {

    companion object {
        fun initialise(): RunnersState {
            return RunnersState(
                exception = null,
                status = Status.Initialise,
                loading = false,
                runners = emptyList(),
                race = null,
                raceId = 0
            )
        }
        fun loading(): RunnersState {
            return RunnersState(
                exception = null,
                status = Status.Loading,
                loading = true,
                runners = emptyList(),
                race = null,
                raceId = 0
            )
        }
    }

    sealed class Status {
        data object Initialise : Status()
        data object Loading : Status()
        data object Success : Status()
        data object Failure : Status()
    }
}
