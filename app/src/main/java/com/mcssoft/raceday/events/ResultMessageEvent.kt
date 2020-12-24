package com.mcssoft.raceday.events

/**
 * Generic class that implements a ResultMessage type Event.
 * @param res: A value representing the result.
 * @param ms: An optional message (or blank).
 */
class ResultMessageEvent(private val res: Int, private var msg: String = "") {

    val result: Int
        get() = res

    val message: String
        get() = msg

    // TBA - other fields as req.
}
