package com.mcssoft.raceday.ui.components.meetings

import com.mcssoft.raceday.domain.model.Meeting

data class MeetingsState(
    val exception: Exception?,
    val status: Status?,
    val message: String,
    val data: List<Meeting>?,
    val date: String
) {
    companion object {
        // Flow initializer.
        fun initialise(date: String): MeetingsState {
            return MeetingsState(
                exception = null,
                status = Status.Initialise,
                message = "",
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
    }

    val body: List<Meeting>
        get() = this.data ?: emptyList()

    val mtgDate: String
        get() = this.date
}