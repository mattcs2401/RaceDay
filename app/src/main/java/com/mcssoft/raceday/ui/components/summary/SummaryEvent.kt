package com.mcssoft.raceday.ui.components.summary

sealed class SummaryEvent {

    data object Refresh: SummaryEvent()
}
