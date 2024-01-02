package com.mcssoft.raceday.utility.notification

interface IAlarmScheduler {

    fun scheduleAlarm(alarmItem: AlarmItem?)

    fun cancelAlarm()

    fun cancelAlarm(alarmItem: AlarmItem?)
}