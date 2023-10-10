package com.mcssoft.racedaybasic.ui.splash

import android.app.Activity

sealed class SplashEvent {

//    data object ErrorDto: SplashEvent()

    data class Error(val activity: Activity): SplashEvent()
}
