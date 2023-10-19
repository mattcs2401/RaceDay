package com.mcssoft.racedaybasic.ui.components.runners

import com.mcssoft.racedaybasic.domain.model.Race
import com.mcssoft.racedaybasic.domain.model.Runner

data class RunnersState(
    val exception: Exception? = null,
    val status: Status = Status.NoState,
    val loading: Boolean = false,
    val runners: List<Runner> = emptyList(),
    val race: Race? = null,
    val raceId: Long = 0
    ) {

    companion object {
        fun initialise(): RunnersState {
            return RunnersState(
                exception = null,
                status = Status.Initialise,
                race = null
            )
        }
    }

    sealed class Status {
        data object Initialise : Status()
        data object Loading : Status()
        data object Success : Status()
        data object Failure : Status()
        data object NoState: Status()
    }
}
