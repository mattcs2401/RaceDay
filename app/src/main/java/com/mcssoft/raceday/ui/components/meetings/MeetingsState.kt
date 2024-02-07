package com.mcssoft.raceday.ui.components.meetings

import com.mcssoft.raceday.domain.model.Meeting

data class MeetingsState(
    val exception: Exception?,
    val status: Status?,
    val data: List<Meeting>?,
    val date: String,
    var canRefresh: Boolean
) {
    companion object {
        // Flow initializer.
        fun initialise(date: String): MeetingsState {
            return MeetingsState(
                exception = null,
                status = Status.Initialise,
                data = null,
                date = date,
                canRefresh = false
            )
        }
    }

    sealed class Status {
        data object Initialise : Status()
        data object Success : Status()
        data object Failure : Status()
    }

    val body: List<Meeting>
        get() = this.data ?: emptyList()

    val mtgDate: String
        get() = this.date
}