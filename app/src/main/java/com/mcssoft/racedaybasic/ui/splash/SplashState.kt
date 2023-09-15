package com.mcssoft.racedaybasic.ui.splash

data class SplashState(
    val date: String = "",
    val response: Int = 0,
    val exception: Exception?,
    val status: Status,
    val loading: Boolean = false,
    val loadingMsg: String,
    val baseFromApi: Boolean = false,
    val baseFromLocal: Boolean = false,
    val runnerFromApi: Boolean = false,
) {
    companion object {
        fun initialise(): SplashState {
            return SplashState(
                exception = null,
                status = Status.Initialise,
                loadingMsg = "Initialising."
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
