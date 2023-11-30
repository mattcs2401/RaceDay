package com.mcssoft.raceday.ui.components.runners

import com.mcssoft.raceday.domain.model.Race
import com.mcssoft.raceday.domain.model.Runner

data class RunnersState(
    val exception: Exception?,
    val status: Status,
    val loading: Boolean,
    val runners: List<Runner>,
    val race: Race?,
    var raceId: Long,
    val checked: Boolean
) {

    companion object {
        fun initialise(): RunnersState {
            return RunnersState(
                exception = null,
                status = Status.Initialise,
                loading = false,
                runners = emptyList(),
                race = null,
                raceId = 0,
                checked = false
            )
        }
        fun loading(): RunnersState {
            return RunnersState(
                exception = null,
                status = Status.Loading,
                loading = true,
                runners = emptyList(),
                race = null,
                raceId = 0,
                checked = false
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
