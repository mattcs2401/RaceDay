package com.mcssoft.raceday.ui.components.races

import androidx.datastore.core.DataStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.raceday.data.repository.preferences.app.AppPreferences
import com.mcssoft.raceday.domain.usecase.UseCases
import com.mcssoft.raceday.utility.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RacesViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle,
//    private val appPrefs: DataStore<AppPreferences>
) : ViewModel() {

    private val _state = MutableStateFlow(RacesState.initialise())
    val state = _state.asStateFlow()

    init {
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
    }

    private fun getRaces(mId: Long) {
        useCases.getRaces(mId).onEach { result ->
            when {
                result.loading -> {
                    _state.update { state ->
                        state.copy(
                            exception = null,
                            status = RacesState.Status.Loading,
                            loading = true
                        )
                    }
                }
                result.failed -> {
                    _state.update { state ->
                        state.copy(
                            exception = result.exception,
                            status = RacesState.Status.Failure,
                            loading = false
                        )
                    }
                }
                result.successful -> {
                    _state.update { state ->
                        state.copy(
                            exception = null,
                            status = RacesState.Status.Success,
                            loading = false,
                            lRaces = result.data ?: emptyList()
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getMeeting(mId: Long) {
        useCases.getMeeting(mId).onEach { result ->
            when {
                result.loading -> {
                    _state.update { state ->
                        state.copy(
                            exception = null,
                            status = RacesState.Status.Loading,
                            loading = true
                        )
                    }
                }
                result.failed -> {
                    _state.update { state ->
                        state.copy(
                            exception = result.exception,
                            status = RacesState.Status.Failure,
                            loading = false
                        )
                    }
                }
                result.successful -> {
                    _state.update { state ->
                        state.copy(
                            exception = null,
                            status = RacesState.Status.Success,
                            loading = false,
                            mtg = result.data,
                            //mtgId = mId
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

}
