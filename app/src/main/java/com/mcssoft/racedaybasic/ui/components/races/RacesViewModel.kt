package com.mcssoft.racedaybasic.ui.components.races

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.racedaybasic.data.repository.preferences.Preference
import com.mcssoft.racedaybasic.data.repository.preferences.PreferencesImpl
import com.mcssoft.racedaybasic.domain.usecase.RaceDayUseCases
import com.mcssoft.racedaybasic.utility.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RacesViewModel @Inject constructor(
    private val raceDayUseCases: RaceDayUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(RacesState.initialise())
    val state = _state.asStateFlow()

    private var mtgId = 0L

    init {
        /*
          The Races screen expects a "meetingId" (supplied in the navigation from the MeetingsScreen
          to the RacesScreen). However, when navigating back from the Runners screen, there is no
           need to supply one, so the original is saved into the preferences and reused.
         */
        savedStateHandle.get<Long>(Constants.KEY_MEETING_ID)?.let { mId ->
            if (mId > 0) {
                mtgId = mId
                // Save the Meeting id to the preferences (for back nav from Runners screen).
                saveMeetingId(Preference.MeetingIdPref, mtgId)
            } else {
                // Get the Meeting id from the preferences.
                getMeetingId(Preference.MeetingIdPref)
                // Meeting id is returned in the state.
                mtgId = _state.value.mtgId
            }
            // Get Meeting and Races values for the screen.
            getMeeting(mtgId)
            getRaces(mtgId)
        }
    }

    fun onEvent(event: RacesEvent) {
        when(event) {
            is RacesEvent.Retry -> {
                mtgId = event.mtgId    // TBA ?
            }
            is RacesEvent.Cancel -> {
                // TBA ?
            }
            else -> {}
        }
    }

    private fun getRaces(mId: Long) {
        raceDayUseCases.getRaces(mId).onEach { result ->
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
        raceDayUseCases.getMeeting(mId).onEach { result ->
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
                            mtgId = mId
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Save the meeting id to the preferences.
     */
    private fun saveMeetingId(pref: Preference.MeetingIdPref, meetingId: Long) {
        raceDayUseCases.savePreferences(pref, meetingId).onEach { result ->
            when {
                result.loading -> {}
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
                            mtgId = meetingId
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Get the meeting id from the preferences.
     */
    private fun getMeetingId(pref: Preference.MeetingIdPref) {
        raceDayUseCases.getPreferences(pref).onEach { result ->
            when {
                result.loading -> {}
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
                            mtgId = result.data as Long
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}
