package com.mcssoft.raceday.hilt

import android.app.NotificationChannel
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

    @Singleton
    @Provides
    fun provideNotificationChannel(@ApplicationContext context: Context): NotificationChannel {
        return NotificationChannel(
            context.resources.getString(R.string.notify_channel_id),
            context.resources.getString(R.string.notify_channel_name),
            android.app.NotificationManager.IMPORTANCE_DEFAULT
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
        val ncb = NotificationCompat.Builder(context, context.resources.getString(R.string.notify_channel_id)).also {
            it.setAutoCancel(false)
            it.setPriority(NotificationCompat.PRIORITY_DEFAULT)
            it.setStyle(NotificationCompat.DecoratedCustomViewStyle())
        }
        return ncb
    }
}
