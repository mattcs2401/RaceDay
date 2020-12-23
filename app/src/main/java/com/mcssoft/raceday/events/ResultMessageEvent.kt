package com.mcssoft.raceday.events

class ResultMessageEvent(private val res: Int, private var msg: String?) {

    val result: Int
        get() = res

    val message: String?
        get() = msg
    // TBA - other fields as req.
}
