package com.mcssoft.racedaybasic.ui.meetings

import com.mcssoft.racedaybasic.domain.model.Meeting

data class MeetingsState(
    val exception: Exception?,
    val status: Status,
    val loading: Boolean = false,
    val data: List<Meeting>?
) {
    companion object {
        // Simply a Flow initializer.
        fun initialise(): MeetingsState {
            return MeetingsState(
                exception = null,
                status = Status.Initialise,
                loading = false,
                data = null
            )
        }
    }

    sealed class Status {
        object Initialise : Status()
        object Loading : Status()
        object Success : Status()
        object Failure : Status()
    }

    val body: List<Meeting>
        get() = this.data ?: emptyList()
}