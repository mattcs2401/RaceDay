package com.mcssoft.raceday.ui.components.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.data.repository.preferences.app.PrefsRepo
import com.mcssoft.raceday.domain.model.Summary
import com.mcssoft.raceday.ui.components.summary.SummaryState.Status
import com.mcssoft.raceday.utility.Constants.TWENTY_FIVE
import com.mcssoft.raceday.utility.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val prefsRepo: PrefsRepo,
    private val iDbRepo: IDbRepo
) : ViewModel() {

    private val _state = MutableStateFlow(SummaryState.initialise())
    val state = _state.asStateFlow()

    init {
        getSummaries()
    }

    fun onEvent(event: SummaryEvent) {
        when (event) {
            is SummaryEvent.Refresh -> {
                getSummaries()
            }
            is SummaryEvent.Removal -> {
                removeSummary(event.summaryId)
            }
            is SummaryEvent.SaveRunnerId -> {
                prefsRepo.runnerId = event.runnerId
            }
        }
    }

    private fun getSummaries() {
        viewModelScope.launch(Dispatchers.IO) {
            val pSummaries = mutableListOf<Summary>()
            val cSummaries = mutableListOf<Summary>()
            try {
                iDbRepo.getSummaries()
                    .toMutableList()
                    .also { summaries ->
                        if (summaries.isNotEmpty()) {
                            val currentTimeMillis = DateUtils().getCurrentTimeMillis()
                            for (summary in summaries) {
                                val raceTime = DateUtils().getCurrentTimeMillis(summary.raceStartTime)
                                if (!summary.isPastRaceTime) {
                                    if (currentTimeMillis > raceTime) {
                                        summary.isPastRaceTime = true
                                        iDbRepo.updateSummary(summary)
                                    }
                                } else {
                                    // A change was made to the Race start time by the TimePicker
                                    // which doesn't update the isPastRaceTime.
                                    if (currentTimeMillis < raceTime) {
                                        summary.isPastRaceTime = false
                                        iDbRepo.updateSummary(summary)
                                    }
                                }
                            }
                            summaries.partition {
                                it.isPastRaceTime
                            }.also { pair ->
                                pSummaries.addAll(
                                    pair.first.sortedBy { it.raceStartTime }
                                )
                                cSummaries.addAll(
                                    pair.second.sortedBy { it.raceStartTime }
                                )
                            }
                        }
                    }
                delay(TWENTY_FIVE)
                _state.update { state ->
                    state.copy(
                        exception = null,
                        status = Status.Success,
                        cSummaries = cSummaries,
                        pSummaries = pSummaries
                    )
                }
                _state.emit(state.value)
            } catch(ex: Exception) {
                _state.update { state ->
                    state.copy(
                        exception = ex,
                        status = Status.Failure
                    )
                }
                _state.emit(state.value)
            }
        }
    }

    private fun removeSummary(summaryId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            iDbRepo.deleteSummary(summaryId)
            delay(TWENTY_FIVE) // TBA.
            getSummaries()
        }
    }
}
