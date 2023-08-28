package com.mcssoft.racedaybasic.ui.meetings

import com.mcssoft.racedaybasic.domain.model.Meeting

data class MeetingsState(
    val exception: Exception?,
    val status: Status,
    val loadingMsg: String,
    val loading: Boolean = false,
    val data: List<Meeting>?,
    val date: String
) {
    companion object {
        // Simply a Flow initializer.
        fun initialise(date: String): MeetingsState {
            return MeetingsState(
                exception = null,
                status = Status.Initialise,
                loadingMsg = "",
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
    }

    val body: List<Meeting>
        get() = this.data ?: emptyList()

    val mtgDate: String
        get() = this.date
}