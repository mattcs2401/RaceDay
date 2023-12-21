package com.mcssoft.raceday.utility.notification

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

interface INotification {

    fun getNotificationManager(): NotificationManagerCompat

    fun getNotificationBuilder(): NotificationCompat.Builder

    fun getNotificationBuilder(title: String?, text: String?): NotificationCompat.Builder
}