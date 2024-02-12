package com.mcssoft.raceday.ui.components.summary

import com.mcssoft.raceday.domain.model.Summary

data class SummaryState(
    val exception: Exception?,
    val status: Status,
    val loading: Boolean,
    val cSummaries: List<Summary>, // current Summaries, i.e. those not past race time.
    val pSummaries: List<Summary>, // previous Summaries, i.e. those past race time.
    val fromApi: Boolean

) {

    companion object {
        // Flow initializer.
        fun initialise(): SummaryState {
            return SummaryState(
                exception = null,
                status = Status.Initialise,
                loading = false,
                cSummaries = emptyList(),
                pSummaries = emptyList(),
                fromApi = false
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
