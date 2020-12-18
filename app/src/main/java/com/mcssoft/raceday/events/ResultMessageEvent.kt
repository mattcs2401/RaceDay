package com.mcssoft.raceday.events

class ResultMessageEvent(private val res: Int) {

    val result: Int
        get() = res

    // TBA - other fields as req.
}
