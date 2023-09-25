package com.mcssoft.racedaybasic.ui.splash

import android.app.Activity

sealed class SplashEvent {

//    data object Error: SplashEvent()

    data class Error(val activity: Activity): SplashEvent()
}
