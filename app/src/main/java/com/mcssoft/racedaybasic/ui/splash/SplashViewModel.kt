package com.mcssoft.racedaybasic.ui.splash

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.racedaybasic.domain.usecase.RaceDayUseCases
import com.mcssoft.racedaybasic.utility.DataResult
import com.mcssoft.racedaybasic.utility.DateUtils
import com.mcssoft.racedaybasic.utility.network.ConnectivityObserver
import com.mcssoft.racedaybasic.utility.network.ConnectivityState
import com.mcssoft.racedaybasic.utility.network.ConnectivityState.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val connectivityObserver: ConnectivityObserver,
    private val raceDayUseCases: RaceDayUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(SplashState.initialise())
    val state = _state.asStateFlow()

    private var connectivityState by mutableStateOf(ConnectivityState.initialise())
//        private set

    init {
        getConnectivity()

        when (connectivityState) {
            is Status.Available -> {
                val date = DateUtils().getDateToday()
                _state.update { state -> state.copy(date = date, hasInternet = true) }
                viewModelScope.launch {
                    _state.emit(state.value)
                }
//                setupBaseFromApi(date)
            }
            is Status.Unavailable -> {
                _state.update { state -> state.copy(hasInternet = false) }
                viewModelScope.launch {
                    _state.emit(state.value)
                }
            }
            else -> {}
        }
    }

    private fun getConnectivity() {
        viewModelScope.launch {
            connectivityObserver.observe().collect { status ->
                connectivityState = status
            }
        }
    }

    fun onEvent(event: SplashEvent) {
        when(event) {
            is SplashEvent.Error -> {
                event.activity.finishAndRemoveTask()
            }
        }
    }

    /**
     * Use case: SetupBaseFromApi.
     * Get the raw data from the Api (Meetings and Races).
     */
    private fun setupBaseFromApi(date: String) {
        viewModelScope.launch {
            raceDayUseCases.setupBaseFromApi(date).collect { result ->
                when (result.status) {
                    is DataResult.Status.Loading -> {
                        stateLoading(msg = "Loading base from API.")
                    }
                    is DataResult.Status.Error -> {
                        Log.d("TAG", "[SplashViewModel] result.error: " + result.errorCode)
                        stateError(result.errorCode)
                    }
                    is DataResult.Status.Failure -> {
                        stateFailure(result)
                    }
                    is DataResult.Status.Success -> {
                        Log.d("TAG", "[SplashViewModel] result.successful")
                        stateSuccess(result.errorCode)
                    }
                    else -> {}
                }
            }
        }
    }

    //<editor-fold default state="collapsed" desc="Region: Utility methods">
    private fun stateLoading(msg: String) {
        _state.update { state ->
            state.copy(
                loading = true,
                status = SplashState.Status.Loading,
                loadingMsg = msg
            )
        }
    }

    private fun stateError(errorCode: Int) {
        _state.update { state ->
            state.copy(
                exception = null,
                response = errorCode,
                status = SplashState.Status.Error,
                loading = false,
                loadingMsg = "An error occurred."
            )
        }
    }

    private fun stateSuccess(code: Int) {
        _state.update { state ->
            state.copy(
                exception = null,
                response = code,
                status = SplashState.Status.Success,
                loading = false,
                loadingMsg = "Setup Runner from Api success."
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
    //</editor-fold>
}