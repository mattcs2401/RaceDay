package com.mcssoft.raceday.utility

import com.mcssoft.raceday.utility.Constants.FIVE
import com.mcssoft.raceday.utility.Constants.SIXTY
import com.mcssoft.raceday.utility.Constants.TEN
import com.mcssoft.raceday.utility.Constants.THOUSAND
import java.util.Calendar
import java.util.Calendar.HOUR_OF_DAY
import java.util.Calendar.MINUTE
import java.util.Locale

class DateUtils {
/*
    getDateToday(): String  - Get today's date in format "YYYY-M(M)-D(D)".
    getTime(String): String - Get the time component (HH:MM) from the given date/time value.
    getCurrentTimeFormatted(): Sting - Get the current time formatted as HH:MM.
    getCurrentTimeFormatted(Long): String - Get the current time formatted as HH:MM from the millis param.
    getCurrentTimeMillis(): Long - Get the current time as a millis value.
    getCurrentTimeMillis(String): Long - Get the current time as a millis value from the format param HH:MM.
 */

    /**
     * Compare the given time value as greater than the current time but less than the window time.
     * @param time: The given time formatted as HH:MI.
     * @param window: A time value in millis that represents a future time.
     * @return [1] The given time is within the window, else [0].
     */
    fun compareToWindowTime(time: String, window: Long?): Boolean {
        val windowTime: Long
        val givenTime = getCurrentTimeMillis(time)
        val currentTime = getCurrentTimeMillis()
        windowTime = window ?: (currentTime + (THOUSAND * SIXTY * FIVE).toLong())

        return (givenTime > currentTime) && (givenTime < windowTime)
    }

    /**
     * Get today's date in format "YYYY-M(M)-D(D)".
     * @return The formatted string.
     */
    fun getDateToday(): String {
        val calendar = Calendar.getInstance(Locale.getDefault())
        val year = calendar.get(Calendar.YEAR).toString()
        var month = ((calendar.get(Calendar.MONTH)) + 1).toString() // Note (1) below.
        var day = calendar.get(Calendar.DAY_OF_MONTH).toString()

        if (((calendar.get(Calendar.MONTH)) + 1) < TEN) month = "0$month"
        if (((calendar.get(Calendar.DAY_OF_MONTH)) + 1) <= TEN) day = "0$day"

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
        if (timeHour[0] == '0') {
            timeHour[0] = '1'
            timeAll[0] = "${timeHour[0]}${timeHour[1]}"
        }

        return "${timeAll[0]}:${timeAll[1]}"
    }

    /**
     * Get the current time formatted as HH:MM.
     */
    fun getCurrentTimeFormatted(): String {
        var hourOfDay: Int
        var minutes: Int
        Calendar.getInstance(Locale.getDefault()).apply {
            hourOfDay = get(HOUR_OF_DAY)
            minutes = get(MINUTE)
        }
        return formatHourMinutes(hourOfDay, minutes)
    }

    /**
     * Get the current time formatted as HH:MM from the parameter.
     * @param millis: A date/time value in millis.
     */
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

    /**
     * Get the current time as a millis value.
     */
    fun getCurrentTimeMillis(): Long {
        val time = getCurrentTimeFormatted().split(":")
        return timeToMillis(time[0].toInt(), time[1].toInt())
    }

    /**
     * Get the current time as a millis value from the formatted param.
     * @param formattedTime: A time value HH:MM.
     */
    fun getCurrentTimeMillis(formattedTime: String): Long {
        val time = formattedTime.split(":")
        return timeToMillis(time[0].toInt(), time[1].toInt())
    }

    /**
     * Utility method to return a formatted String.
     * @param hourOfDay: An hour value.
     * @param minutes: A minutes value.
     */
    private fun formatHourMinutes(hourOfDay: Int, minutes: Int): String {
        return if (minutes < TEN) {
            "$hourOfDay:0$minutes"
        } else {
            "$hourOfDay:$minutes"
        }
    }

    /**
     * Utility method to return time in millis.
     * @param hourOfDay: An hour value.
     * @param minutes: A minutes value.
     */
    private fun timeToMillis(hourOfDay: Int, minutes: Int): Long {
        return Calendar.getInstance(Locale.getDefault()).apply {
            set(HOUR_OF_DAY, hourOfDay)
            set(MINUTE, minutes)
        }.timeInMillis
    }
}
/*
Notes:
(1) - https://developer.android.com/reference/java/util/Calendar:
      The first month of the year in the Gregorian and Julian calendars is JANUARY which is 0.
 */
