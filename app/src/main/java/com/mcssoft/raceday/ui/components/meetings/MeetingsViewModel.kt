package com.mcssoft.raceday.ui.components.meetings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.data.repository.preferences.app.PrefsRepo
import com.mcssoft.raceday.ui.components.meetings.MeetingsState.Status
import com.mcssoft.raceday.utility.Constants.TWENTY_FIVE
import com.mcssoft.raceday.utility.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetingsViewModel @Inject constructor(
    private val iDbRepo: IDbRepo,
    private val prefsRepo: PrefsRepo
) : ViewModel() {

    private val _state = MutableStateFlow(MeetingsState.initialise(DateUtils().getDateToday()))
    val state = _state.asStateFlow()

    init {
        // 1st update the preferences, then mirror into the state.
        _state.update { state ->
            state.copy(
                status = Status.Initialise,
                canRefresh = prefsRepo.fromApi
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            _state.emit(state.value)
        }
        prefsRepo.clearIds()
        // Get a list of the Meetings that have been populated into the database.
        getMeetings()
    }

    fun onEvent(event: MeetingEvent) {
        when (event) {
            is MeetingEvent.RefreshMeeting -> {
                // TBA.
            }
            is MeetingEvent.SaveMeetingId -> {
                prefsRepo.meetingId = event.meetingId
            }
        }
    }

    /**
     * Get a list of Meetings from the database.
     * @note Database is already populated from the Api.
     */
    private fun getMeetings() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val meetings = iDbRepo.getMeetings().sortedBy { meeting ->
                    meeting.meetingTime
                }
                delay(TWENTY_FIVE) // TBA.
                _state.update { state ->
                    state.copy(
                        exception = null,
                        status = Status.Success,
                        data = meetings
                    )
                }
                _state.emit(state.value)
            } catch(ex: Exception) {
                _state.update { state ->
                    state.copy(
                        exception = ex,
                        status = Status.Failure,
                        data = null
                    )
                }
                _state.emit(state.value)
            }
        }
    }
}

