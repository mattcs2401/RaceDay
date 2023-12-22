package com.mcssoft.raceday.utility.notification

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.telephony.ServiceState
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

    private var binder = LocalBinder()
    private var allowRebind: Boolean = true

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

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onUnbind(intent: Intent): Boolean {
        // All clients have unbound with unbindService()
        return allowRebind
    }

    override fun onRebind(intent: Intent) {
        // A client is binding to the service with bindService(),
        // after onUnbind() has already been called
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            NotificationState.START_SERVICE.toString() -> start()         // defined below.
            NotificationState.STOP_SERVICE.toString() -> stopSelf()       //in-built.

        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = notificationBldr
            .setContentTitle("Notification Service")
            .setContentText("The Notification service is active.")
            .setAutoCancel(true)
            .setSilent(true)
            .build()
        startForeground(1, notification)
    }

    /**
     * Doco: Class used for the client Binder. Because we know this service always runs in the same
     *       process as its clients, we don't need to deal with IPC.
     */
    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods.
        fun getService(): NotificationService = this@NotificationService
    }
}