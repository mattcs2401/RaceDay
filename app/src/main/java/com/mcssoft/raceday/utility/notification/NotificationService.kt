package com.mcssoft.raceday.utility.notification

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mcssoft.raceday.R
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

// Ref: https://www.youtube.com/watch?v=YZL-_XJSClc
// Ref: https://medium.com/@stevdza-san/create-a-basic-notification-in-android-b0d4fd29ad89

class NotificationService: Service() {
// Note: Can't constructor inject here.

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface INotifyEntryPoint {
        fun getNotificationManager(): INotification
        fun getNotificationBuilder(): INotification
    }

    private lateinit var builder: INotification
    private lateinit var notificationMgr: NotificationManagerCompat
    private lateinit var notificationBldr: NotificationCompat.Builder

    override fun onCreate() {
//    super.onCreate()
    val entryPoints =
        EntryPointAccessors.fromApplication(this, INotifyEntryPoint::class.java)
        val notification = entryPoints.getNotificationManager()
        builder = entryPoints.getNotificationBuilder()

        notificationMgr = notification.getNotificationManager()
        notificationBldr = builder.getNotificationBuilder()
    }

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
        val notification = notificationBldr
            .setContentTitle("Notify is active")
            .setContentText("Notify text")
            .build()
//        startForeground(1, notification)
        startForeground(1, null)
    }

    enum class Actions {
        START, STOP
    }
}