package com.mcssoft.raceday.utility.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.mcssoft.raceday.utility.DateUtils
import javax.inject.Inject

class AlarmSchedulerImpl @Inject constructor(
    private val context: Context,
    private val alarmManager: AlarmManager
): IAlarmScheduler {

    override fun scheduleAlarm(alarmItem: AlarmItem?) {
        Log.d("TAG", "Alarm scheduled.")
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            //putExtra("EXTRA_MESSAGE", alarmItem?.data)
            putExtra("EXTRA_MESSAGE", "The message of the Intent.")
        }

        val pIntent = PendingIntent.getBroadcast(
            context,
            alarmItem.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // TODO - trigger times from app prefs ?
        // Trigger in about 1 minute.
        val alarmTriggerTime = DateUtils().getCurrentTimeMillis() + (1000 * 60).toLong()
        // Recur approx every 5 minutes.
        val alarmIntervalTime = (1000 * 60 * 5).toLong()

//        alarmManager.setRepeating(
//            AlarmManager.RTC_WAKEUP,
//            alarmTriggerTime,
//            alarmIntervalTime,
//            pIntent
//        )
    }

    override fun cancelAlarm() {
        Log.d("TAG", "Alarm cancelled.")
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                this.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancelAlarm(alarmItem: AlarmItem?) {
        Log.d("TAG", "Alarm cancelled.")
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
