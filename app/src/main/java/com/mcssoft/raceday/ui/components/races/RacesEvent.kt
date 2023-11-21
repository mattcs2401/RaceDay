package com.mcssoft.raceday.ui.components.races

sealed class RacesEvent {

    data class Retry(val mtgId: Long = 0): RacesEvent()

    data object Cancel: RacesEvent()

}