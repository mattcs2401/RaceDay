package com.mcssoft.raceday.ui.components.meetings

import com.mcssoft.raceday.domain.model.Meeting

data class MeetingsState(
    val response: Int = 0,
    val exception: Exception?,
    val status: Status = Status.NoState,
    val message: String,
    val loading: Boolean = false,
    val data: List<Meeting>?,
    val date: String,
    val customExType: String? = null,
    val customExMsg: String? = null,
) {
    companion object {
        // Simply a Flow initializer.
        fun initialise(date: String): MeetingsState {
            return MeetingsState(
                exception = null,
                status = Status.Initialise,
                message = "",
                loading = false,
                data = null,
                date = date
            )
        }
    }

    sealed class Status {
        data object Initialise : Status()
        data object Loading : Status()
        data object Success : Status()
        data object Failure : Status()
        data object Error: Status()
        data object NoState: Status()
    }

    val body: List<Meeting>
        get() = this.data ?: emptyList()

    val mtgDate: String
        get() = this.date
}