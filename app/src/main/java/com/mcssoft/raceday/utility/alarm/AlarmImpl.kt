package com.mcssoft.raceday.utility.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.mcssoft.raceday.utility.Constants
import com.mcssoft.raceday.utility.DateUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlarmImpl @Inject constructor(
    private val context: Context,
    private val alarmManager: AlarmManager
) : IAlarm {
/*
  Note:
  The AlarmReceiver does the actual check if there are Summaries or not, e.g. Summary items may not
  exist initially, or added automatically,  but they could be added manually later.
 */
    override fun scheduleAlarm() {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("TAG", "Alarm scheduled.")
            val alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(
                    context,
                    this.hashCode(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            }

            // TODO - trigger times from app prefs ?
            // Trigger in about 30 seconds.
            val alarmTriggerTime = DateUtils().getCurrentTimeMillis() + Constants.THIRTY_SECONDS
            // Recur about every 5 minutes.
            val alarmIntervalTime = Constants.FIVE_MINUTES

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                alarmTriggerTime,
                alarmIntervalTime,
                alarmIntent
            )
        }
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
}
