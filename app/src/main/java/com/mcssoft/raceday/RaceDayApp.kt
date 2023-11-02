package com.mcssoft.raceday

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RaceDayApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val channel = NotificationChannel(
            this.resources.getString(R.string.download_channel_id),
            this.resources.getString(R.string.download_channel_name),
            NotificationManager.IMPORTANCE_HIGH
        )

//        val channel = NotificationChannel(
//            "notify_channel",
//            "Notifications",
//            NotificationManager.IMPORTANCE_HIGH
//        )
        // TODO - integrate the notifications.
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager// NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

}