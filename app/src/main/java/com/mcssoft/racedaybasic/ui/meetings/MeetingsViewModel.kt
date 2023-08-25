package com.mcssoft.racedaybasic.ui.meetings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.racedaybasic.domain.usecase.RaceDayUseCases
import com.mcssoft.racedaycompose.ui.meetings.MeetingsEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetingsViewModel @Inject constructor(
    private val raceDayUseCases: RaceDayUseCases,
//    private val prefs: IPreferences,
) : ViewModel() {

    private val _state = MutableStateFlow(MeetingsState.initialise())
    val state = _state.asStateFlow()

//    private val _prefState = MutableStateFlow(PrefState.initialise())
//    val prefState = _prefState.asStateFlow()

    init {
        Log.d("TAG", "enter MeetingsViewModel")

        viewModelScope.launch {
//            val onlyAuNzPref = prefs.getPreference(Preference.OnlyAuNzPref) as Boolean

            // Update PrefState.
//            _prefState.update { state -> state.copy(byAuNzPref = onlyAuNzPref) }

            getMeetingsFromLocal()//onlyAuNzPref)
        }
    }

    /**
     * Called from the Meetings screen. Do a complete reload of the Api data.
     */
    fun onEvent(event: MeetingsEvent) {
        // TBA.
    }

    /**
     * Use case: GetMeetings.
     * Get a list of Meetings from the database.
     * @note Database is already populated.
     */
    private fun getMeetingsFromLocal() {//onlyAuNz: Boolean) {
        viewModelScope.launch {
            raceDayUseCases.getMeetings().collect { result ->
                when {
                    result.loading -> {
                        _state.update { state ->
                            state.copy(
                                status = MeetingsState.Status.Loading,
                                loading = true
                            )
                        }
                    }
                    result.failed -> {
                        _state.update { state ->
                            state.copy(
                                exception = Exception(result.exception),
                                status = MeetingsState.Status.Failure,
                                loading = false
                            )
                        }
                    }
                    result.successful -> {
                        Log.d("TAG", "getMeetingsFromLocal(onlyAuNz) result.successful")
                        _state.update { state ->
                            state.copy(
                                exception = null,
                                status = MeetingsState.Status.Success,
                                loading = false,
                                data = result.data ?: emptyList()
                            )
                        }
                    }
                }
            }//.launchIn(viewModelScope)
        }
    }

}