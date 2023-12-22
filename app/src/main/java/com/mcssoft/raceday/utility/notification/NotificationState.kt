package com.mcssoft.raceday.utility.notification

sealed class NotificationState {

    data object START_SERVICE: NotificationState()

    data object STOP_SERVICE: NotificationState()
}