package com.mcssoft.racedaybasic.ui.splash

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.racedaybasic.domain.usecase.RaceDayUseCases
import com.mcssoft.racedaybasic.utility.DataResult
import com.mcssoft.racedaybasic.utility.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val raceDayUseCases: RaceDayUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(SplashState.initialise())
    val state: StateFlow<SplashState> = _state

    init {
        val date = DateUtils().getDateToday()
        _state.update { state -> state.copy(date = date) }

        setupBaseFromApi(date)
    }

    /**
     * Use case: SetupBaseFromApi.
     * Get the raw data from the Api (Meetings and Races).
     */
    private fun setupBaseFromApi(date: String) {
        viewModelScope.launch {
            raceDayUseCases.setupBaseFromApi(date).collect { result ->
                when(result.status) {
                    is DataResult.Status.Loading -> {
                        _state.update { state ->
                            state.copy(
                                loading = true,
                                status = SplashState.Status.Loading,
                                loadingMsg = "Loading base from API."
                            )
                        }
                    }
                    is DataResult.Status.Error -> {
                        Log.d("TAG", "[SplashViewModel] result.error: " + result.errorCode)
                        _state.update { state ->
                            state.copy(
                                exception = null,
                                response = result.errorCode,
                                status = SplashState.Status.Error,
                                loading = false,
                                loadingMsg = "An error occurred."
                            )
                        }
                    }
                    is DataResult.Status.Failure -> {
                        if (state.value.response == 0) {
                            if (result.exception == null) {
                                _state.update { state ->
                                    state.copy(
                                        customExType = result.customExType,
                                        customExMsg = result.customExMsg,
                                        status = SplashState.Status.Failure,
                                        loading = false,
                                    )
                                }
                            } else {
                                val exceptionText = result.exception.message ?: "Exception"
                                Log.d("TAG", "[SplashViewModel] result.failed: $exceptionText")

                                _state.update { state ->
                                    state.copy(
                                        exception = Exception(exceptionText),
                                        status = SplashState.Status.Failure,
                                        loading = false,
                                        loadingMsg = "An Exception error occurred."
                                    )
                                }
                            }
                        } else {
                            _state.update { state ->
                                state.copy(
                                    exception = null,
                                    status = SplashState.Status.Failure,
                                    loading = false,
                                    response = result.errorCode
                                )
                            }
                        }
                    }
                    is DataResult.Status.Success -> {
                        Log.d("TAG", "[SplashViewModel] result.successful")
                        _state.update { state ->
                            state.copy(
                                exception = null,
                                response = result.errorCode,
                                status = SplashState.Status.Success,
                                loading = false,
                                baseFromApi = true,
                                loadingMsg = "Setup base from API success."
                            )
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    /**
     * Use case: SetupRunnersFromApi.
     * Get the raw data from the Api (Runners).
     * Note: This has to be done separately from the Meeting & Race info because of the Api. Runner
     *       info is per Meeting code, not in the generic list of Meetings and Races. So the Api has
     *       to be hit for each Meeting code.
     */
//    fun setupRunnersFromApi(context: Context) {
//        viewModelScope.launch {
//            delay(250) // TBA ?
//            raceDayUseCases.setupRunnerFromApi(_state.value.date, context).collect { result ->
//                when {
//                    result.loading -> {
//                        _state.update { state ->
//                            state.copy(
//                                loading = true,
//                                status = SplashState.Status.Loading,
//                                loadingMsg = "Loading Runners from API."
//                            )
//                        }
//                    }
//                    result.failed -> {
//                        _state.update { state ->
//                            state.copy(
//                                exception = Exception("[SetupRunnerFromApi] ${result.exception}"),
//                                status = SplashState.Status.Failure,
//                                loading = false,
//                                loadingMsg = "An error occurred."
//                            )
//                        }
//                    }
//                    result.successful -> {
//                        Log.d("TAG", "[SetupRunnersFromApi] result.successful")
//                        _state.update { state ->
//                            state.copy(
//                                exception = null,
//                                status = SplashState.Status.Success,
//                                loading = false,
//                                runnerFromApi = true,
//                                loadingMsg = "Setup Runner from API success."
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }

/*
    private fun checkPrePopulate() {
        raceDayUseCases.checkPrePopulate().onEach { result ->
            when {
                result.loading -> {
                    _state.update { state ->
                        state.copy(
                            exception = null,
                            status = SplashState.Status.Loading,
                            loading = true
                        )
                    }
                }
                result.failed -> {
                    _state.update { state ->
                        state.copy(
                            exception = result.exception ?: Exception("An unknown error has occurred."),
                            status = SplashState.Status.Failure,
                            loading = false
                        )
                    }
                }
                result.successful -> {
                    _state.update { state ->
                        state.copy(
                            exception = null,
                            status = SplashState.Status.Success,
                            loading = false,
                            prePopulated = (result.data == true)
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
*/
/*
    fun prePopulate() {
        raceDayUseCases.prePopulate().onEach { result ->
            when {
                result.loading -> {
                    _state.update { state ->
                        state.copy(
                            exception = null,
                            status = SplashState.Status.Loading,
                            loading = true
                        )
                    }
                }
                result.failed -> {
                    _state.update { state ->
                        state.copy(
                            exception = result.exception ?: Exception("An unknown error has occurred."),
                            status = SplashState.Status.Failure,
                            loading = false
                        )
                    }
                }
                result.successful -> {
                    _state.update { state ->
                        state.copy(
                            exception = null,
                            status = SplashState.Status.Success,
                            loading = false,
                            prePopulated = (result.data == true)
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
 */
}