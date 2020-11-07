package com.mcssoft.raceday.utility

import android.content.Context
import com.mcssoft.raceday.R
import java.util.*

object RaceDayUtil {

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
}

/*
Notes:
(1) - https://developer.android.com/reference/java/util/Calendar:
      The first month of the year in the Gregorian and Julian calendars is JANUARY which is 0.

 */