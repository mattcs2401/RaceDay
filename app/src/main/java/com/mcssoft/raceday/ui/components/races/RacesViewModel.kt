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
    private val iDbRepo: IDbRepo,
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
//            getMeeting(mtgId)
//            getRaces(mtgId)
            getMeetingWithRaces(mtgId)
        }
    }

    fun onEvent(event: RacesEvent) {
        when (event) {
            is RacesEvent.DateChange -> {
                viewModelScope.launch {
                    iDbRepo.updateRaceTime(event.raceId, event.time)
                }
            }
        }
    }

    private fun getMeetingWithRaces(mId: Long) {
        /*
        Note: Room lets yo return a structure of e.g. Map<Meeting, List<Race>>, but the Key is
              anonymous, it is actually the Meeting object. Not sure if there's any performance
              overhead there.
         */
        // TODO - trim down the Meeting values returned by the query, we don't need all of them for
        //        the header info.
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = iDbRepo.getMeetingWithRaces(mId)

                val meeting = result.keys.elementAt(0) // the keys are a Set with only one value.
                val races = result.getValue(meeting)

                delay(TWENTY_FIVE) // TBA

                _state.update { state ->
                    state.copy(
                        status = Status.Success,
                        meeting = meeting,
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
}
