package com.mcssoft.raceday.ui.components.splash

import android.app.Activity

sealed class SplashEvent {

    data class Error(val activity: Activity): SplashEvent()

    data class SetFromApi(val value: Boolean): SplashEvent()

    data object SetRunners: SplashEvent()
}
