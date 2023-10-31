package com.mcssoft.racedaybasic.ui.components.splash

import android.app.Activity

sealed class SplashEvent {

    data class Error(val activity: Activity): SplashEvent()

    data object SetRunners : SplashEvent()
}
