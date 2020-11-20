package com.mcssoft.raceday.utility

import android.content.Context
import com.mcssoft.raceday.R
import java.util.*

object RaceDayUtilities {

    enum class DateFormat {
        SLASH,
        DASH
    }

    /**
     * Create the tatts.com main page Url based on today's date.
     * E.g. tatts.com/pagedata/racing/YYYY/M(M)/D(D)/RaceDay.xml
     *
     * @param context: Used to access system string resources.
     * @return The formatted Url.
     */
    fun createRaceDayUrl(context: Context): String {
        val baseUrl = context.resources.getString(R.string.base_url)
        val datePart = getDateToday(DateFormat.SLASH)
        val mainPage = context.resources.getString(R.string.main_page)
        return "$baseUrl$datePart$mainPage"
    }

    /**
     * Get today's date in format; (1) "YYYY/MM/DD" or (2) "YYYY-MM-DD"
     * @param dateFormat: DateFormat.SLASH, or DateFormat.DASH
     * @return The formatted string.
     */
    fun getDateToday(dateFormat: DateFormat): String {
        val calendar = Calendar.getInstance(Locale.getDefault())
        val year = calendar.get(Calendar.YEAR).toString()
        val month = ((calendar.get(Calendar.MONTH)) + 1).toString()  // Note (1) below.
        val day = calendar.get(Calendar.DAY_OF_MONTH).toString()

        return when(dateFormat) {
            DateFormat.SLASH -> "$year/$month/$day/"
            DateFormat.DASH -> "$year-$month-$day/"
        }
    }

    /**
     * Compare the Day & Month of the given time value to today's Day & Month.
     * @param timeVal: The time value to compare against.
     * @return True if the Day & Month of the given time value is equal today's Day & Month value.
     */
    fun compareDateToToday(timeVal: Long): Boolean {
        val calendarToday = Calendar.getInstance(Locale.getDefault())
        val dayToday = calendarToday.get(Calendar.DAY_OF_MONTH).toString()
        val monthToday = calendarToday.get(Calendar.MONTH).toString()

        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.timeInMillis = timeVal
        val day = calendar.get(Calendar.DAY_OF_MONTH).toString()
        val month = calendar.get(Calendar.MONTH).toString()

        if((day == dayToday) && (month == monthToday)) {
            return true
        }
        return false
    }

    /**
     * Create the filename for the downloaded RaceDay xml data.
     * @param context: Used to access system string resources.
     * @return The file name as "YYYY-MM-DD_RaceDay.xml".
     */
    fun createRaceDayFilename(context: Context): String {
        val datePart = getDateToday(DateFormat.DASH)
        val under = "_"
        val mainPage = context.resources.getString(R.string.main_page)
        return "$datePart$under$mainPage"
    }

    /**
     * Get the time from the parameter.
     * @param timeInMillis: The time value in mSec.
     * @return The time as HH:MM.
     */
    fun timeFromMillis(timeInMillis: Long): String {
        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.timeInMillis = timeInMillis
        var hour = calendar.get(Calendar.HOUR_OF_DAY).toString()
        var minute = calendar.get(Calendar.MINUTE).toString()

        if(hour.length < 2) hour = "0$hour"
        if(minute.length < 2) minute = "0$minute"

        return "$hour:$minute"
    }
}

/*
Notes:
(1) - https://developer.android.com/reference/java/util/Calendar:
      The first month of the year in the Gregorian and Julian calendars is JANUARY which is 0.

 */