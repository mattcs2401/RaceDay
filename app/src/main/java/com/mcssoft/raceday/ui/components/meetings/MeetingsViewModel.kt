package com.mcssoft.raceday.ui.components.meetings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.raceday.domain.usecase.UseCases
import com.mcssoft.raceday.ui.components.meetings.MeetingsState.Status
import com.mcssoft.raceday.utility.DataResult
import com.mcssoft.raceday.utility.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetingsViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _state = MutableStateFlow(MeetingsState.initialise(DateUtils().getDateToday()))
    val state = _state.asStateFlow()

    init {
        // Get a list of the Meetings that have been populated into the database.
        getMeetingsFromLocal()
    }

    /**
     * Use case: GetMeetings.
     * Get a list of Meetings from the database.
     * @note Database is already populated.
     */
    private fun getMeetingsFromLocal() {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getMeetings()
                .catch { exception ->
                    // Note 1 below.
                    emit(DataResult.failure(exception as Exception))
                }
                .collect { result ->
                    when {
                        result.loading -> {
                            _state.update { state ->
                                state.copy(
                                    exception = null,
                                    status = Status.Loading,
                                    message = "Loading Meetings ..."
                                )
                            }
                            _state.emit(state.value)
                        }
                        result.failed -> {
                            _state.update { state ->
                                state.copy(
                                    exception = Exception(result.exception),
                                    status = Status.Failure,
                                )
                            }
                            _state.emit(state.value)
                        }
                        result.successful -> {
                            _state.update { state ->
                                state.copy(
                                    exception = null,
                                    status = Status.Success,
                                    data = result.data ?: emptyList()
                                )
                            }
                            _state.emit(state.value)
                        }
                    }
            }
        }

    }

}
/*
Notes:
1. When an exception occurs, the collect lambda is called, as a new item has been emitted to the
   stream because of the exception
   Ref: https://developer.android.com/kotlin/flow
 */