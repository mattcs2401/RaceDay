package com.mcssoft.raceday.utility.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import javax.inject.Inject

class AlarmSchedulerImpl @Inject constructor(
    private val context: Context,
    private val alarmManager: AlarmManager
): IAlarmScheduler {

    override fun schedule(alarmItem: AlarmItem) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("EXTRA_MESSAGE", alarmItem.data)
        }

        val pIntent = PendingIntent.getBroadcast(
            context,
            alarmItem.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // TODO - when do we actually trigger, and what are we displaying.
        val alarmTriggerTime = alarmItem.alarmTriggerTime.timeInMillis
        val alarmIntervalTime = alarmItem.alarmIntervalTime.timeInMillis + (5000 * 60)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, alarmTriggerTime, alarmIntervalTime, pIntent)
    }

    override fun cancel(alarmItem: AlarmItem) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarmItem.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}
