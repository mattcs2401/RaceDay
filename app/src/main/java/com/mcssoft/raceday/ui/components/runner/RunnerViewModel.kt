package com.mcssoft.raceday.ui.components.runner

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.raceday.domain.usecase.UseCases
import com.mcssoft.raceday.ui.components.runner.RunnerState.Status
import com.mcssoft.raceday.utility.Constants
import com.mcssoft.raceday.utility.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RunnerViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(RunnerState.initialise())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<Long>(Constants.KEY_RUNNER_ID)?.let { runnerId ->
            // Get Race and Runner values for the screen.
            getRunner(runnerId)
        }
    }

    /**
     * Get the Runners associated with a Race from the database.
     * @param runnerId: The Runner id (_id value).
     */
    private fun getRunner(runnerId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getRunner(runnerId)
                .catch { exception ->
                    emit(DataResult.failure(exception as Exception))
                }
                .collect { result ->
                    when {
                        result.loading -> {
                            _state.update { state ->
                                state.copy(
                                    exception = null,
                                    status = Status.Loading,
                                    loading = true
                                )
                            }
                            _state.emit(state.value)
                        }
                        result.failed -> {
                            _state.update { state ->
                                state.copy(
                                    exception = result.exception,
                                    status = Status.Failure,
                                    loading = false
                                )
                            }
                            _state.emit(state.value)
                        }
                        result.successful -> {
                            _state.update { state ->
                                state.copy(
                                    exception = null,
                                    status = Status.Success,
                                    loading = false,
                                    runner = result.data
                                )
                            }
                            _state.emit(state.value)
                        }
                    }
                }

        }
    }
}