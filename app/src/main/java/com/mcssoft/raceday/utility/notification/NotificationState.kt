package com.mcssoft.raceday.utility.notification

data class NotificationState(
    val status: Status
) {

    sealed class Status {
        data object Notified: Status()
        data object NotNotified: Status()
        data object NotApplicable: Status()
    }
}