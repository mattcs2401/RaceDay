package com.mcssoft.raceday.utility

import java.util.*
import java.util.Calendar.HOUR
import java.util.Calendar.HOUR_OF_DAY
import java.util.Calendar.MINUTE

class DateUtils {

    /**
     * Get today's date in format "YYYY-M(M)-D(D)".
     * @return The formatted string.
     */
    fun getDateToday(): String {
        val calendar = Calendar.getInstance(Locale.getDefault())
        val year = calendar.get(Calendar.YEAR).toString()
        var month = ((calendar.get(Calendar.MONTH)) + 1).toString()  // Note (1) below.
        var day = calendar.get(Calendar.DAY_OF_MONTH).toString()

        if(((calendar.get(Calendar.MONTH)) + 1) < 10) month = "0$month"
        if(((calendar.get(Calendar.DAY_OF_MONTH)) + 1) <= 10) day = "0$day"

        return "$year-$month-$day"
    }

    /**
     * Get the time component (HH:MM) from the given date/time value.
     * @param dateTime: A value in the format "YYYY-MM-DDTHH:MM:SS".
     * @return Time value formatted as "HH:MI".
     */
    fun getTime(dateTime: String): String {
        val timeAll = dateTime.split("T")[1].split(":").toMutableList()

        // Sometimes hour value is, e.g. 02:14 when should be 12:14.
        val timeHour = timeAll[0].toCharArray()
        if(timeHour[0] == '0') {
            timeHour[0] = '1'
            timeAll[0] = "${timeHour[0]}${timeHour[1]}"
        }

        return "${timeAll[0]}:${timeAll[1]}"
    }

    fun getCurrentTimeFormatted(): String {
        var hourOfDay: Int
        var minutes: Int
        Calendar.getInstance(Locale.getDefault()).apply {
            hourOfDay = get(HOUR_OF_DAY)
            minutes = get(MINUTE)
        }
        return formatHourMinutes(hourOfDay, minutes)
    }

    fun getCurrentTimeFormatted(millis: Long): String {
        var hourOfDay: Int
        var minutes: Int
        Calendar.getInstance(Locale.getDefault()).apply {
            timeInMillis = millis
            hourOfDay = get(HOUR_OF_DAY)
            minutes = get(MINUTE)
        }
        return formatHourMinutes(hourOfDay, minutes)
    }

    fun getCurrentTimeMillis(): Long {
        val time = getCurrentTimeFormatted().split(":")
        return timeToMillis(time[0].toInt(), time[1].toInt())
    }

    fun getCurrentTimeMillis(formattedTime: String): Long {
        val time = formattedTime.split(":")
        return timeToMillis(time[0].toInt(), time[1].toInt())
    }

//    /**
//     * Is the current time after the given (Race) time.
//     * @param givenTime: The time to compare against.
//     */
//    fun isAfter(givenTime: Long) : Boolean {
//        return Calendar.getInstance(Locale.getDefault()).timeInMillis > givenTime
//    }

    /**
     * Utility method to return a formatted String.
     */
    private fun formatHourMinutes(hourOfDay: Int, minutes: Int): String {
        return if(minutes < 10) {
            "$hourOfDay:0$minutes"
        } else {
            "$hourOfDay:$minutes"
        }
    }

    /**
     * Utility method to return time in millis from a format string ("HH:MM").
     */
    private fun timeToMillis(hourOfDay: Int, minute: Int) : Long {
        return Calendar.getInstance(Locale.getDefault()).apply {
            set(HOUR_OF_DAY, hourOfDay)
            set(MINUTE, minute)
        }.timeInMillis
    }
}
/*
Notes:
(1) - https://developer.android.com/reference/java/util/Calendar:
      The first month of the year in the Gregorian and Julian calendars is JANUARY which is 0.
 */

