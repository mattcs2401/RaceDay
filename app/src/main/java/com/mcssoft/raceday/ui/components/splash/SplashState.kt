package com.mcssoft.raceday.ui.components.splash

data class SplashState(
    val date: String = "",
    val response: Int = 0,
    val exception: Exception? = null,
    val status: Status? = null,
    val loading: Boolean = false,
    val loadingMsg: String = "",
    val customExType: String? = null,
    val customExMsg: String? = null,
    val hasInternet: Boolean = true,
    val sourceFromApi: Boolean = true
) {
    companion object {
        // Flow initializer.
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
