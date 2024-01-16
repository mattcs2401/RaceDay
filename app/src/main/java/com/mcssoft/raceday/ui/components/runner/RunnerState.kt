package com.mcssoft.raceday.ui.components.runner

import com.mcssoft.raceday.domain.model.Runner

data class RunnerState(
    val exception: Exception?,
    val status: Status,
    val loading: Boolean,
    val runner: Runner?
) {

    companion object {
        fun initialise(): RunnerState {
            return RunnerState(
                exception = null,
                status = Status.Initialise,
                loading = false,
                runner = null
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
