package com.mcssoft.raceday.ui.components.races

sealed class RacesEvent {

    /**
     *
     */
    data class DateChange(val raceId: Long, val time: String) : RacesEvent()

    /**
     *
     */
    data class SaveRaceId(val raceId: Long) : RacesEvent()
}