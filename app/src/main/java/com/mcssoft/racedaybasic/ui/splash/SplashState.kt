package com.mcssoft.racedaybasic.ui.splash

data class SplashState(
    val date: String = "",
    val response: Int = 0,
    val exception: Exception?,
    val status: Status,
    val loading: Boolean = false,
    val loadingMsg: String = "",
    val baseFromApi: Boolean = false,
    val customExType: String? = null,
    val customExMsg: String? = null,
//    val baseFromLocal: Boolean = false,
    val runnerFromApi: Boolean = false,
    val hasInternet: Boolean = true
) {
    companion object {
        fun initialise(): SplashState {
            return SplashState(
                exception = null,
                status = Status.Initialise,
                loadingMsg = "Initialising."
            )
        }
        fun failure(ex: Exception): SplashState {
            return SplashState(
                exception = ex,
                status = Status.Failure
            )
        }
        fun failure(type: String, msg: String): SplashState {
            return SplashState(
                exception = null,
                status = Status.Failure,
                customExType = type,
                customExMsg = msg
            )
        }
    }

    sealed class Status {
        data object Initialise : Status()
        data object Loading : Status()
        data object Success : Status()
        data object Failure : Status()
        data object Error: Status()
    }
}
