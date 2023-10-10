package com.mcssoft.racedaybasic.ui.meetings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.racedaybasic.data.repository.preferences.PreferencesImpl
import com.mcssoft.racedaybasic.domain.usecase.RaceDayUseCases
import com.mcssoft.racedaybasic.ui.splash.SplashEvent
import com.mcssoft.racedaybasic.utility.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetingsViewModel @Inject constructor(
    private val raceDayUseCases: RaceDayUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(MeetingsState.initialise(DateUtils().getDateToday()))
    val state = _state.asStateFlow()

    init {
        Log.d("TAG", "enter MeetingsViewModel")
        _state.update { state ->
            state.copy(
                loading = true,
                status = MeetingsState.Status.Loading,
                loadingMsg = "Loading Meetings listing."
            )
        }
        // Get a list of the Meetings that have been populated into the database.
        getMeetingsFromLocal()
    }

    fun onEvent(event: MeetingsEvent) {
//        when(event) {
//            else -> {}
//        }
    }

    /**
     * Use case: GetMeetings.
     * Get a list of Meetings from the database.
     * @note Database is already populated.
     */
    private fun getMeetingsFromLocal() {
        viewModelScope.launch(Dispatchers.IO) {
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
            }//.launchIn(viewModelScope)
        }
    }

//    /**
//     * Use case: SetupRunnersFromApi.
//     * Get the raw data from the Api (Runners).
//     * Note: This has to be done separately from the Meeting & Race info because of the Api. Runner
//     *       info is per ??.
//     */
//    fun setupRunnersFromApi(context: Context) {
//        viewModelScope.launch {
//            delay(250) // TBA ?
//            raceDayUseCases.setupRunnersFromApi(context).collect { result ->
//                when(result.status) {
//                    is DataResult.Status.Loading -> {
//                        stateLoading("Loading Runners from API.")
//                    }
//                    is DataResult.Status.ErrorDto -> {
//                        Log.d("TAG", "[SplashViewModel] result.errorDto: " + result.errorCode)
//                        stateError(result.errorCode)
//                    }
//                    is DataResult.Status.Success -> {
//                        Log.d("TAG", "[SplashViewModel] result.successful")
//                        stateSuccess(result.errorCode)
//                    }
//                    is DataResult.Status.Failure -> {
//                        stateFailure(result)
//                    }
//                    else -> {}
//                }
//            }
//        }
//    }
}