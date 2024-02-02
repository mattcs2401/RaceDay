package com.mcssoft.raceday.ui.components.runners

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.raceday.data.repository.preferences.PrefsRepo
import com.mcssoft.raceday.domain.model.Race
import com.mcssoft.raceday.domain.model.Runner
import com.mcssoft.raceday.domain.usecase.UseCases
import com.mcssoft.raceday.ui.components.runners.RunnersState.Status
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
class RunnersViewModel @Inject constructor(
    private val useCases: UseCases,
    private val prefsRepo: PrefsRepo,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(RunnersState.initialise())
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
        /*
          The Runners screen expects a "raceId" (supplied in the navigation from the RacesScreen
          to the RunnersScreen).
         */
        savedStateHandle.get<Long>(Constants.KEY_RACE_ID)?.let { raceId ->
            // Get Race and Runner values for the screen.
            getRace(raceId)
            getRunners(raceId)
        }
    }

    /**
     * Events raised from the UI.
     * @param event: The event type.
     */
    fun onEvent(event: RunnersEvent) {
        when (event) {
            is RunnersEvent.Check -> {
                setRunnerChecked(event.race, event.runner)
            }
        }
    }

    /**
     * Set the metadata 'checked' on the Runner object.
     * @param runner: The Runner.
     */
    private fun setRunnerChecked(race: Race, runner: Runner) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.setRunnerChecked(race, runner)
                .catch { exception ->
                    // Note 1 below.
                    emit(DataResult.failure(exception as Exception))
                }
                .collect { result ->
                    when {
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
                                    checked = result.data.toBoolean()
                                )
                            }
                            _state.emit(state.value)
                        }
                    }
            }
        }
    }

    /**
     * Get the Runners associated with a Race from the database.
     * @param raceId: The Race id (_id value).
     */
    private fun getRunners(raceId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getRunners(raceId)
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
                                    lRunners = result.data
                                )
                            }
                            _state.emit(state.value)
                        }
                    }
            }

        }
    }

    /**
     * Get the Race object from the database.
     * @param raceId: The Race id (_id value).
     */
    private fun getRace(raceId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getRace(raceId).collect { result ->
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
                                race = result.data
                            )
                        }
                        _state.emit(state.value)
                    }
                }
            }
        }
    }


}