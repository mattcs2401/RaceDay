package com.mcssoft.raceday

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RaceDayApp : Application() {

//    override fun onCreate() {
//        super.onCreate()
//
//        val channel = NotificationChannel(
//            this.resources.getString(R.string.notify_channel_id),
//            this.resources.getString(R.string.notify_channel_name),
//            NotificationManager.IMPORTANCE_HIGH
//        )
//
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(channel)
//    }

}
