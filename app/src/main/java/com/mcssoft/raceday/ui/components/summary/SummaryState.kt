package com.mcssoft.raceday.ui.components.summary

import com.mcssoft.raceday.domain.model.Summary

data class SummaryState(
    val exception: Exception?,
    val status: Status,
    val loading: Boolean,
    val summaries: List<Summary>,
//    val race: Race?,
//    val raceId: Long,
//    val scratched: Boolean,
//    val checked: Boolean
) {

    companion object {
        fun initialise(): SummaryState {
            return SummaryState(
                exception = null,
                status = Status.Initialise,
                loading = false,
                summaries = emptyList(),
            )
        }
        fun loading(): SummaryState {
            return SummaryState(
                exception = null,
                status = Status.Loading,
                loading = true,
                summaries = emptyList(),
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

