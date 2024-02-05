package com.mcssoft.raceday.ui.components.races

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.data.repository.preferences.PrefsRepo
import com.mcssoft.raceday.domain.usecase.UseCases
import com.mcssoft.raceday.ui.components.races.RacesState.Status
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
class RacesViewModel @Inject constructor(
    private val dbRepo: IDbRepo,
    private val useCases: UseCases,
    private val prefsRepo: PrefsRepo,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(RacesState.initialise())
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
          The Races screen expects a "meetingId" (supplied in the navigation from the MeetingsScreen
          to the RacesScreen).
         */
        savedStateHandle.get<Long>(Constants.KEY_MEETING_ID)?.let { mtgId ->
            // Get Meeting and Races values for the screen.
            getMeeting(mtgId)
            getRaces(mtgId)
        }
    }

    fun onEvent(event: RacesEvent) {
        when (event) {
            is RacesEvent.DateChange -> {
                viewModelScope.launch {
                    dbRepo.updateRaceTime(event.raceId, event.time)
                }
            }
        }
    }

    private fun getRaces(mId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getRaces(mId)
                .catch { exception ->
                    emit(DataResult.failure(exception as Exception))
                }
                .collect { result ->
                    when {
                        result.loading -> {
                            _state.update { state ->
                                state.copy(
                                    exception = null,
                                    status = Status.Loading
                                )
                            }
                            _state.emit(state.value)
                        }
                        result.failed -> {
                            _state.update { state ->
                                state.copy(
                                    exception = result.exception,
                                    status = Status.Failure
                                )
                            }
                            _state.emit(state.value)
                        }
                        result.successful -> {
                            _state.update { state ->
                                state.copy(
                                    exception = null,
                                    status = Status.Success,
                                    lRaces = result.data
                                )
                            }
                            _state.emit(state.value)
                        }
                    }
            }
        }
    }

    private fun getMeeting(mId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getMeeting(mId)
                .catch { exception ->
                    emit(DataResult.failure(exception as Exception))
                }
                .collect { result ->
                    when {
                        result.loading -> {
                            _state.update { state ->
                                state.copy(
                                    exception = null,
                                    status = Status.Loading
                                )
                            }
                            _state.emit(state.value)
                        }
                        result.failed -> {
                            _state.update { state ->
                                state.copy(
                                    exception = result.exception,
                                    status = Status.Failure
                                )
                            }
                            _state.emit(state.value)
                        }
                        result.successful -> {
                            _state.update { state ->
                                state.copy(
                                    exception = null,
                                    status = Status.Success,
                                    meeting = result.data,
                                )
                            }
                            _state.emit(state.value)
                        }
                    }
            }
        }
    }
}
