package com.mcssoft.raceday.ui.components.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.data.repository.preferences.PrefsRepo
import com.mcssoft.raceday.domain.usecase.UseCases
import com.mcssoft.raceday.utility.Constants.TWENTY_FIVE
import com.mcssoft.raceday.utility.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val prefsRepo: PrefsRepo,
    private val useCases: UseCases,
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
            useCases.getSummaries()
                .catch {
                    emit(DataResult.failure(it as Exception))
                }
                .collect { result ->
                    when {
                        result.loading -> {
                            _state.update {
                                it.copy(
                                    exception = null,
                                    status = SummaryState.Status.Loading,
                                    loading = true
                                )
                            }
                            _state.emit(state.value)
                        }
                        result.failed -> {
                            _state.update {
                                it.copy(
                                    exception = result.exception,
                                    status = SummaryState.Status.Failure,
                                    loading = false
                                )
                            }
                            _state.emit(state.value)
                        }
                        result.successful -> {
                            _state.update {
                                it.copy(
                                    exception = null,
                                    status = SummaryState.Status.Success,
                                    loading = false,
                                    summaries = result.data ?: emptyList()
                                )
                            }
                            _state.emit(state.value)
                        }
                    }
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
