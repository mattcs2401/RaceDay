package com.mcssoft.raceday.events

import com.mcssoft.raceday.utility.Constants

class ResultMessageEvent(private val res: Int) {

    val result: Int
        get() = res

    // TBA - other fields as req.
}
