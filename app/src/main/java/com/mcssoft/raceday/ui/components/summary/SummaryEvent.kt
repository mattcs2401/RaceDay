package com.mcssoft.raceday.ui.components.summary

import com.mcssoft.raceday.domain.model.Summary

sealed class SummaryEvent {

    /**
     * Get the Summary listing (again).
     */
    data object Refresh : SummaryEvent()

    /**
     * Remove a Summary item from the listing.
     * @param summaryId: The id of the Summary.
     */
    data class Removal(val summaryId: Long) : SummaryEvent()

    /**
     *
     */
    data class SaveRunnerId(val  runnerId: Long) : SummaryEvent()

    /**
     *
      */
    data class Check(val summary: Summary): SummaryEvent()
}
