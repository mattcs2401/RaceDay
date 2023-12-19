package com.mcssoft.raceday.utility.notification

interface IAlarmScheduler {
    fun schedule(alarmItem: AlarmItem)
    fun cancel(alarmItem: AlarmItem)
}