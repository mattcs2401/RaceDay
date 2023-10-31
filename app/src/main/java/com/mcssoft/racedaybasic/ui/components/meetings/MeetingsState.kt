package com.mcssoft.racedaybasic.ui.components.meetings

import com.mcssoft.racedaybasic.domain.model.Meeting

data class MeetingsState(
    val response: Int = 0,
    val exception: Exception?,
    val status: Status = Status.NoState,
    val loadingMsg: String,
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
        data object Error: Status()
        data object NoState: Status()
    }

    val body: List<Meeting>
        get() = this.data ?: emptyList()

    val mtgDate: String
        get() = this.date
}