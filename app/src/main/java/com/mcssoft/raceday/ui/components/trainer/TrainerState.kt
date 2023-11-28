package com.mcssoft.raceday.ui.components.trainer

import com.mcssoft.raceday.domain.model.Trainer

data class TrainerState(
    val exception: Exception?,
    val status: Status,
    val loading: Boolean,
    val trainers: List<Trainer>
) {
    companion object {
        fun initialise(): TrainerState {
            return TrainerState(
                exception = null,
                status = Status.Initialise,
                loading = false,
                trainers = emptyList()
            )
        }
        fun loading(): TrainerState {
            return TrainerState(
                exception = null,
                status = Status.Loading,
                loading = true,
                trainers = emptyList(),
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

