package com.mcssoft.raceday.interfaces

/**
 * Interface between the RaceMeetingAdapter and the RaceMeetingViewHolder. Implemented by the
 * RaceMeetingAdapter and passed as a constructor parameter to the RaceMeetingViewHolder.
 */
interface IAdapter {
    /**
     * The delete of a view from the list.
     * @param position: The view's position within the adapter.
     */
    fun delete(position: Int)

    /**
     * Signify that a type selection was made.
     * @param selType: The selection type - TBA.
     * @param position: The adapter position.
     */
    fun onTypeSelect(selType: Int, position: Int)

}