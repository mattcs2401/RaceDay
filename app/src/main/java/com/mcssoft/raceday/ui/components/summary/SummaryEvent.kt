package com.mcssoft.raceday.ui.components.summary

sealed class SummaryEvent {

    // Get the Summary listing (again).
    data object Refresh : SummaryEvent()

    // Remove a Summary item from the listing.
    data class Removal(val summaryId: Long) : SummaryEvent()
}
