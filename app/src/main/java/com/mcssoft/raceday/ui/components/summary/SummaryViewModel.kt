package com.mcssoft.raceday.ui.components.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.data.repository.preferences.PrefsRepo
import com.mcssoft.raceday.ui.components.summary.SummaryState.Status
import com.mcssoft.raceday.utility.Constants.TWENTY_FIVE
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
        _state.update { state ->
            state.copy(
                fromApi = prefsRepo.fromApi
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            _state.emit(state.value)
        }
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
        }
    }

    private fun getSummaries() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val summaries = iDbRepo.getSummaries()
                delay(TWENTY_FIVE)
                _state.update { state ->
                    state.copy(
                        exception = null,
                        status = Status.Success,
                        summaries = summaries
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
