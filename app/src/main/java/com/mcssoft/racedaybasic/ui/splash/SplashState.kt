package com.mcssoft.racedaybasic.ui.splash

data class SplashState(
    val date: String = "",
    val response: Int = 0,
    val exception: Exception? = null,
    val status: Status = Status.NoState,
    val loading: Boolean = false,
    val loadingMsg: String = "",
    val customExType: String? = null,
    val customExMsg: String? = null,
    val hasInternet: Boolean = true
) {
    companion object {
        // Flow initializer. TBA
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
        data object NoState: Status()
    }
}
