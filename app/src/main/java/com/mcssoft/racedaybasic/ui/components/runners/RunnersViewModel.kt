package com.mcssoft.racedaybasic.ui.components.runners

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.racedaybasic.data.repository.preferences.Preference
import com.mcssoft.racedaybasic.domain.model.Runner
import com.mcssoft.racedaybasic.domain.usecase.RaceDayUseCases
import com.mcssoft.racedaybasic.utility.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RunnersViewModel @Inject constructor(
    private val raceDayUseCases: RaceDayUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(RunnersState.initialise())
    val state = _state.asStateFlow()

    private var raceId = 0L

    init {
        /*
          The Runners screen expects a "raceId" (supplied in the navigation from the RacesScreen
          to the RunnersScreen). However, when navigating back from the Runners screen, there is no
           need to supply one, so the original is saved into the preferences and reused.
         */
        savedStateHandle.get<Long>(Constants.KEY_RACE_ID)?.let { rId ->
            if (rId > 0) {
                raceId = rId
                // Save the Race id to the preferences (for back nav from Runners screen).
                saveRaceId(Preference.RaceIdPref, raceId)
            } else {
                // Get the Race id from the preferences.
                getRaceId(Preference.RaceIdPref)
                // Race id is returned in the state.
                raceId = state.value.raceId
            }
            // Get Race and Runner values for the screen.
            getRace(raceId)
            getRunners(raceId)
        }
    }

    fun onEvent(event: RunnersEvent) {
        when(event) {
            is RunnersEvent.Check -> {
                // TODO - create/update Summary record.
                setRunnerChecked(event.runner)
            }
        }
    }

    private fun setRunnerChecked(runner: Runner) {
        viewModelScope.launch(Dispatchers.IO) {
            raceDayUseCases.setRunnerChecked(runner).collect { result ->
                when {
                    result.failed -> {
                        _state.update { state ->
                            state.copy(
                                exception = result.exception,
                                status = RunnersState.Status.Failure,
                                loading = false
                            )
                        }
                    }
                    result.successful -> {
                        _state.update { state ->
                            state.copy(
                                exception = null,
                                status = RunnersState.Status.Success,
                                loading = false,
                                checked = result.data.toBoolean()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getRunners(raceId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            raceDayUseCases.getRunners(raceId).collect { result ->
                when {
                    result.loading -> {
                        _state.update { state ->
                            state.copy(
                                exception = null,
                                status = RunnersState.Status.Loading,
                                loading = true
                            )
                        }
                    }
                    result.failed -> {
                        _state.update { state ->
                            state.copy(
                                exception = result.exception,
                                status = RunnersState.Status.Failure,
                                loading = false
                            )
                        }
                    }
                    result.successful -> {
                        _state.update { state ->
                            state.copy(
                                exception = null,
                                status = RunnersState.Status.Success,
                                loading = false,
                                runners = result.data ?: emptyList()
                            )
                        }
                    }
                }
            }

        }
    }

    private fun getRace(rId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            raceDayUseCases.getRace(rId).collect { result ->
                when {
                    result.loading -> {
                        _state.update { state ->
                            state.copy(
                                exception = null,
                                status = RunnersState.Status.Loading,
                                loading = true
                            )
                        }
                    }
                    result.failed -> {
                        _state.update { state ->
                            state.copy(
                                exception = result.exception,
                                status = RunnersState.Status.Failure,
                                loading = false
                            )
                        }
                    }
                    result.successful -> {
                        _state.update { state ->
                            state.copy(
                                exception = null,
                                status = RunnersState.Status.Success,
                                loading = false,
                                race = result.data
                            )
                        }
                    }
                }
            }
        }
    }

    /**
     * Save the Race id to the preferences.
     */
    private fun saveRaceId(pref: Preference.RaceIdPref, raceId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            raceDayUseCases.savePreferences(pref, raceId).collect { result ->
                when {
                    result.loading -> {}
                    result.failed -> {
                        _state.update { state ->
                            state.copy(
                                exception = result.exception,
                                status = RunnersState.Status.Failure,
                                loading = false
                            )
                        }
                    }
                    result.successful -> {
                        _state.update { state ->
                            state.copy(
                                exception = null,
                                status = RunnersState.Status.Success,
                                loading = false,
                                raceId = raceId
                            )
                        }
                    }
                }
            }
        }
    }

    /**
     * Get the meeting id from the preferences.
     */
    private fun getRaceId(pref: Preference.RaceIdPref) {
        viewModelScope.launch(Dispatchers.IO) {
            raceDayUseCases.getPreferences(pref).collect { result ->
                when {
                    result.loading -> {}
                    result.failed -> {
                        _state.update { state ->
                            state.copy(
                                exception = result.exception,
                                status = RunnersState.Status.Failure,
                                loading = false
                            )
                        }
                    }
                    result.successful -> {
                        _state.update { state ->
                            state.copy(
                                exception = null,
                                status = RunnersState.Status.Success,
                                loading = false,
                                raceId = result.data as Long
                            )
                        }
                    }
                }
            }
        }
    }

}