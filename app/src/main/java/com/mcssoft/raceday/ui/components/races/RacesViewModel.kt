package com.mcssoft.raceday.ui.components.races

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.data.repository.preferences.PrefsRepo
import com.mcssoft.raceday.ui.components.races.RacesState.Status
import com.mcssoft.raceday.utility.Constants
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
class RacesViewModel @Inject constructor(
    private val dbRepo: IDbRepo,
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
            try {
                val races = dbRepo.getRaces(mId)
                delay(TWENTY_FIVE)
                _state.update { state ->
                    state.copy(
                        status = Status.Success,
                        lRaces = races
                    )
                }
                _state.emit(state.value)
            } catch(ex: Exception) {
                _state.update { state ->
                    state.copy(
                        status = Status.Failure
                    )
                }
                _state.emit(state.value)
            }

        }
    }

    private fun getMeeting(mId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val meeting = dbRepo.getMeeting(mId)
                delay(TWENTY_FIVE)
                _state.update { state ->
                    state.copy(
                        status = Status.Success,
                        meeting = meeting
                    )
                }
                _state.emit(state.value)
            } catch(ex: Exception) {
                _state.update { state ->
                    state.copy(
                        status = Status.Failure,
                        meeting = null
                    )
                }
                _state.emit(state.value)
            }
        }
    }
}
