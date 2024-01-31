package com.mcssoft.raceday.utility.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.mcssoft.raceday.utility.Constants
import com.mcssoft.raceday.utility.DateUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlarmImpl @Inject constructor(
    private val context: Context,
    private val alarmManager: AlarmManager,
    private val coroutineScope: CoroutineScope
) : IAlarm {
/*
  Note:
  The AlarmReceiver does the actual check if there are Summaries or not, e.g. Summary items may not
  exist initially, or added automatically,  but they could be added manually later.
 */
    private lateinit var alarmIntent: Intent
    private lateinit var pendingIntent: PendingIntent

    override fun scheduleAlarm() {
        coroutineScope.launch {
            Log.d("TAG", "Alarm scheduled.")
            alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
                addFlags(Intent.FLAG_RECEIVER_FOREGROUND) // TBA.
            }

            pendingIntent = PendingIntent.getBroadcast(
                context,
                this.hashCode(),
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // TODO - trigger times from some future app prefs ?
            // Trigger in about 30 seconds.
            val alarmTriggerTime = DateUtils().getCurrentTimeMillis() + Constants.THIRTY_SECONDS
            // Recur about every 5 minutes.
            val alarmIntervalTime = Constants.FIVE_MINUTES

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                alarmTriggerTime,
                alarmIntervalTime,
                pendingIntent
            )
        }
    }

    override fun cancelAlarm() {
        Log.d("TAG", "Alarm cancelled.")
        alarmManager.cancel(pendingIntent)
    }
}
