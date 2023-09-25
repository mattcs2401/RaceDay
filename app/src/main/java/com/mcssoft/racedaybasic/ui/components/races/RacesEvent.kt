package com.mcssoft.racedaybasic.ui.components.races

sealed class RacesEvent {

    data class Retry(val mtgId: Long = 0): RacesEvent()

    object Cancel: RacesEvent()

}