package com.mcssoft.raceday.utility.notification

import android.os.Bundle
import java.util.Calendar

data class AlarmItem(
    val alarmTriggerTime: Calendar,
    val alarmIntervalTime: Calendar,
    val data: Bundle
)
/*
public void setRepeating(int type,
            long triggerAtMillis,
            long intervalMillis,
            PendingIntent operation)
triggerAtMillis is the time in milliseconds when the alarm should first fire off,
intervalMillis is the interval in milliseconds after which the alarm should repeat,
and operation is the PendingIntent that will fire each time the alarm goes off.
 */