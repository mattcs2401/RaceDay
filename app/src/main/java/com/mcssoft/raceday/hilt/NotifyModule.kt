package com.mcssoft.raceday.hilt

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PRIVATE
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.mcssoft.raceday.R
import com.mcssoft.raceday.ui.MainActivity
import com.mcssoft.raceday.ui.components.navigation.MY_ARG
import com.mcssoft.raceday.ui.components.navigation.MY_URI
import com.mcssoft.raceday.utility.RaceDayReceiver
import com.mcssoft.raceday.utility.notification.AlarmSchedulerImpl
import com.mcssoft.raceday.utility.notification.IAlarmScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotifyModule {

    @Singleton
    @Provides
    fun provideAlarmManager(
        @ApplicationContext context: Context
    ): AlarmManager {
        return context.getSystemService(AlarmManager::class.java)
    }

    // TODO - provideNotificationBuilder ?
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

    @Singleton
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(context)
        val channel = NotificationChannel(
            context.resources.getString(R.string.notify_channel_id),
            context.resources.getString(R.string.notify_channel_name),
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
        return notificationManager
    }

}