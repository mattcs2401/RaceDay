package com.mcssoft.raceday.utility.alarm

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.mcssoft.raceday.utility.notification.INotification
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class AlarmReceiver: BroadcastReceiver() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface INotifyEntryPoint {
        fun notificationManager(): INotification
        fun notificationBuilder(): INotification
    }

    @SuppressLint("MissingPermission") // Permission is in the manifest, but still complains.
    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("TAG", "BroadcastReceiver.onReceive")

        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return

        val notifyEntryPoints =
            EntryPointAccessors.fromApplication(context, INotifyEntryPoint::class.java)
        val notificationManager = notifyEntryPoints.notificationManager().getNotificationManager()

        // Using 2nd form of.
        val notificationBuilder =
            notifyEntryPoints.notificationBuilder()
                .getNotificationBuilder("Alarm Demo","Notification sent: $message")

//        notificationBuilder
//            .setContentTitle("Alarm Demo")
//            .setContentText("Notification sent with message: $message")
        notificationManager.notify(1, notificationBuilder.build())
    }
}