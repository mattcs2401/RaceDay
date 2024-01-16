package com.mcssoft.raceday.hilt

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mcssoft.raceday.R
import com.mcssoft.raceday.utility.notification.INotification
import com.mcssoft.raceday.utility.notification.NotificationImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotifyModule {
//    * TBA *
//    @Singleton
//    @Provides
//    fun provideAlarmManager(
//        @ApplicationContext context: Context
//    ): AlarmManager {
//        return context.getSystemService(AlarmManager::class.java)
//    }
//
//    @Singleton
//    @Provides
//    fun provideAlarmScheduler(
//        @ApplicationContext context: Context,
//        alarmManger: AlarmManager
//    ): IAlarmScheduler {
//        return AlarmSchedulerImpl(context, alarmManger)
//    }

    @Singleton
    @Provides
    fun provideNotificationChannel(@ApplicationContext context: Context): NotificationChannel {
        return NotificationChannel(
            context.resources.getString(R.string.notify_channel_id),
            context.resources.getString(R.string.notify_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = context.resources.getString(R.string.notify_channel_desc)
        }
    }

    @Singleton
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context,
    ): NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(context)
        val channel = provideNotificationChannel(context)
        notificationManager.createNotificationChannel(channel)
        return notificationManager
    }

    // For Field injection in NotificationService.
    // (NotificationManagerCompat && NotificationCompat.Builder)
    @Singleton
    @Provides
    fun provideFieldNotificationManager(
        @ApplicationContext context: Context,
    ): INotification {
        return NotificationImpl(context)
    }

    // This provides for commonality of the Notifications.
    @Singleton
    @Provides
    fun provideNotificationBuilder(
        @ApplicationContext context: Context,
    ): NotificationCompat.Builder {
//        val intent = Intent(context, AlarmReceiver::class.java).apply {
//            action = "INTENT_ACTION"
//        }
//        val pIntent = PendingIntent.getBroadcast(context,0, intent, PendingIntent.FLAG_IMMUTABLE)
        val ncb = NotificationCompat.Builder(context, context.resources.getString(R.string.notify_channel_id)).also {
            it.setAutoCancel(false)
            it.setPriority(NotificationCompat.PRIORITY_DEFAULT)
            it.setStyle(NotificationCompat.DecoratedCustomViewStyle())
//            it.addAction(0, context.resources.getString(R.string.action_btn_label), pIntent)
        }
        return ncb
    }
}
//    @Singleton
//    @Provides
//    fun provideNotificationBuilder(
//        @ApplicationContext context: Context
//    ): NotificationCompat.Builder {
//        val intent = Intent(context, RaceDayReceiver::class.java).apply {
//            putExtra("MESSAGE", "Clicked!")
//        }
//        val flag =
//            PendingIntent.FLAG_IMMUTABLE
//        val pendingIntent = PendingIntent.getBroadcast(
//            context,
//            0,
//            intent,
//            flag
//        )
//        val clickIntent = Intent(
//            Intent.ACTION_VIEW,
//            "$MY_URI/$MY_ARG=Coming from Notification".toUri(),
//            context,
//            MainActivity::class.java
//        )
//        val clickPendingIntent: PendingIntent = TaskStackBuilder.create(context).run {
//            addNextIntentWithParentStack(clickIntent)
//            getPendingIntent(1, flag)
//        }
//        return NotificationCompat.Builder(context, "Main Channel ID")
//            .setContentTitle("TBA")
//            .setContentText("TBA")
//            .setSmallIcon(R.drawable.ic_notifications_24)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setVisibility(VISIBILITY_PRIVATE)
//            .setPublicVersion(
//                NotificationCompat.Builder(context, "Main Channel ID")
//                    .setContentTitle("Hidden")
//                    .setContentText("Unlock to see the message.")
//                    .build()
//            )
//            .addAction(0, "ACTION", pendingIntent)
//            .setContentIntent(clickPendingIntent)
//    }
