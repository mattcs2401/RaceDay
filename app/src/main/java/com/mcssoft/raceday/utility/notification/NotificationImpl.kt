package com.mcssoft.raceday.utility.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mcssoft.raceday.R

/**
 * Class used to parameter inject NotificationManager and Builder.
 * @param context: Context for String resources and NotificationManager.
 */
class NotificationImpl(
    private val context: Context
) : INotification {

    override fun getNotificationManager(): NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(context)
        val channel = NotificationChannel(
            context.resources.getString(R.string.notify_channel_id),
            context.resources.getString(R.string.notify_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = context.resources.getString(R.string.notify_channel_desc)
        }
        notificationManager.createNotificationChannel(channel)
        return notificationManager
    }

    override fun getNotificationBuilder(): NotificationCompat.Builder {
        return NotificationCompat.Builder(
            context,
            context.resources.getString(R.string.notify_channel_id)
        )
//            .setContentTitle(title)
//            .setContentText(text)
            .setSmallIcon(R.drawable.ic_notifications_24)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }

    override fun getNotificationBuilder(title: String?, text: String?): NotificationCompat.Builder {
        return NotificationCompat.Builder(
            context,
            context.resources.getString(R.string.notify_channel_id)
        )
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_notifications_24)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }
}
