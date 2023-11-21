package com.mcssoft.raceday.utility.services

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.mcssoft.raceday.R

// Based largely on: https://www.youtube.com/watch?v=YZL-_XJSClc
class NotifyService: Service() {

    private lateinit var notification: Notification

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            Actions.START.toString() -> start()       // defined below.
            Actions.STOP.toString() -> stopSelf()     //in-built.

        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        notification = NotificationCompat.Builder(this, "notify_channel_id")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Notify is active")
            .setContentText("Notify text")
            .build()
        startForeground(1, notification)
    }

    enum class Actions {
        START, STOP
    }
}