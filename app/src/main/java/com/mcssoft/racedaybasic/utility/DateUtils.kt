package com.mcssoft.racedaybasic.utility

import java.util.*

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
}
/*
Notes:
(1) - https://developer.android.com/reference/java/util/Calendar:
      The first month of the year in the Gregorian and Julian calendars is JANUARY which is 0.
 */

