package com.mcssoft.raceday.utility.notification

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mcssoft.raceday.R
import com.mcssoft.raceday.utility.alarm.IAlarmScheduler
import com.mcssoft.raceday.utility.notification.NotificationState.START_SERVICE
import com.mcssoft.raceday.utility.notification.NotificationState.STOP_SERVICE
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class NotificationService : Service() {
// Note: Can't constructor inject here.
// Ref: https://dagger.dev/hilt/entry-points.html
//      https://developer.android.com/develop/background-work/services/alarms/schedule

    //    private var binder = LocalBinder()
//    private var allowRebind: Boolean = true

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface INotifyEntryPoint {
        fun notificationManager(): INotification
        fun notificationBuilder(): INotification
    }

    private lateinit var notificationMgr: NotificationManagerCompat
    private lateinit var notificationBldr: NotificationCompat.Builder

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface IAlarmScheduleEntryPoint {
        fun schedule(): IAlarmScheduler
        fun cancel(): IAlarmScheduler
    }

    private lateinit var schedule: IAlarmScheduler
    private lateinit var cancel: IAlarmScheduler

    override fun onCreate() {
//    super.onCreate()
        val notifyEntryPoints =
            EntryPointAccessors.fromApplication(this, INotifyEntryPoint::class.java)
        notificationMgr = notifyEntryPoints.notificationManager().getNotificationManager()
        notificationBldr = notifyEntryPoints.notificationBuilder().getNotificationBuilder()

        val schedulerEntryPoints =
            EntryPointAccessors.fromApplication(this, IAlarmScheduleEntryPoint::class.java)
        schedule = schedulerEntryPoints.schedule()
        cancel = schedulerEntryPoints.cancel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            START_SERVICE.toString() -> start() // defined below.
            STOP_SERVICE.toString() -> stop() //
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        schedule.scheduleAlarm()

        notificationBldr
            .setContentTitle(resources.getString(R.string.notification_service))
            .setContentText(resources.getString(R.string.notification_service_text))
            .setAutoCancel(true)
            .setSilent(true)
            .build()
        startForeground(1, notificationBldr.build())
    }

    private fun stop() {
        cancel.cancelAlarm()
        stopSelf()
    }

    override fun onBind(intent: Intent): IBinder? { return null }

//    override fun onUnbind(intent: Intent): Boolean {
//        // All clients have unbound with unbindService()
//        return true //allowRebind
//    }

//    override fun onRebind(intent: Intent) {
//        // A client is binding to the service with bindService(),
//        // after onUnbind() has already been called
//    }

//    /**
//     * Doco: Class used for the client Binder. Because we know this service always runs in the same
//     *       process as its clients, we don't need to deal with IPC.
//     */
//    inner class LocalBinder : Binder() {
//        // Return this instance of LocalService so clients can call public methods.
//        fun getService(): NotificationService = this@NotificationService
//    }
}
