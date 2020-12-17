package com.mcssoft.raceday.interfaces

/**
 * Interface between the RaceMeetingAdapter and the RaceMeetingHeaderViewHolder. Implemented by the
 * RaceMeetingAdapter and passed as a constructor parameter to the RaceMeetingHeaderViewHolder.
 */
interface IAdapter {
    /**
     * Signify that a type selection was made.
     * @param selType: The selection type (Edit or Copy).
     * @param position: The adapter position.
     */
    fun onTypeSelect(selType: Int, position: Int)
}