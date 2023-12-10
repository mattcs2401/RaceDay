package com.mcssoft.raceday.ui.components.meetings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.raceday.domain.usecase.UseCases
import com.mcssoft.raceday.utility.DataResult
import com.mcssoft.raceday.utility.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        Log.d("TAG", "enter MeetingsViewModel")

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
            useCases.getMeetings().collect { result ->
                when {
                    result.loading -> {
                        stateLoading("Loading Meetings listing.")
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
                        Log.d("TAG", "getMeetingsFromLocal() result.successful")
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
            }
        }
    }

    //<editor-fold default state="collapsed" desc="Region: Utility methods">
    private fun stateLoading(msg: String) {
        _state.update { state ->
            state.copy(
                loading = true,
                status = MeetingsState.Status.Loading,
                message = msg
            )
        }
    }

    private fun stateError(errorCode: Int, msg: String) {
        _state.update { state ->
            state.copy(
                exception = null,
                response = errorCode,
                status = MeetingsState.Status.Error,
                loading = false,
                message = msg
            )
        }
    }

    private fun stateSuccess(code: Int, msg: String) {
        _state.update { state ->
            state.copy(
                exception = null,
                response = code,
                status = MeetingsState.Status.Success,
                loading = false,
                message = msg
            )
        }
    }

    private fun stateFailure(result: DataResult<Any>) {
        if (state.value.response == 0) {
            if (result.exception == null) {
                _state.update { state ->
                    state.copy(
                        customExType = result.customExType,
                        customExMsg = result.customExMsg,
                        status = MeetingsState.Status.Failure,
                        loading = false,
                    )
                }
            } else {
                val exceptionText = result.exception.message ?: "Exception"
                Log.d("TAG", "[MeetingsViewModel] result.failed: $exceptionText")

                _state.update { state ->
                    state.copy(
                        exception = Exception(exceptionText),
                        status = MeetingsState.Status.Failure,
                        loading = false,
                        message = "An Exception error occurred."
                    )
                }
            }
        } else {
            _state.update { state ->
                state.copy(
                    exception = null,
                    status = MeetingsState.Status.Failure,
                    loading = false,
                    response = result.errorCode
                )
            }
        }
    }
    //</editor-fold>
}