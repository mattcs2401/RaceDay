package com.mcssoft.racedaybasic.ui.splash

data class SplashState(
    val date: String = "",
    val exception: Exception?,
    val status: Status,
    val loading: Boolean = false,
    val loadingMsg: String,
    val baseFromApi: Boolean = false,
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
        object Initialise : Status()
        object Loading : Status()
        object Success : Status()
        object Failure : Status()
    }
}
