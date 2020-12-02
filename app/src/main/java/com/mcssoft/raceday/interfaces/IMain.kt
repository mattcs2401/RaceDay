package com.mcssoft.raceday.interfaces

import com.mcssoft.raceday.database.entity.RaceMeeting

/**
 * Interface between the RaceDetailsAdapter and MainFragment.
 * Implemented by the MainFragment and passed as constructor argument to the adapter.
 * Used to reference RaceViewModel -> RaceRepository functionality via the MainFragment.
 */
interface IMain {

    /**
     * Get the count of RaceDetails in the backing data.
     * @return Count value.
     */
    fun getRaceMeetingCount(): Int

    /**
     * Get a RaceDetails object from the backing data at position.
     * @param ndx: A list index into the backing data (position).
     * @return The RaceMeeting object.
     */
    fun getAt(ndx: Int): RaceMeeting

//    /**
//     * Insert the given RaceDetails.
//     * @param raceDetails: The RaceDetails to insert.
//     */
//    fun insert(raceDetails: RaceMeeting): Long

//    /**
//     * Delete the RaceDetails that corresponds to the backing data position.
//     * @param ndx: A list index into the backing data (position).
//     */
//    fun deleteAt(ndx: Int)

//    /**
//     * Notification that the item at position was removed from the list (backing data).
//     * @param position: The index into the list.
//     * @Note: Simply calls the adapter's method of the same name.
//     */
//    fun notifyItemRemoved(position: Int)

//    /**
//     * Signify that a type selection was made.
//     * @param selType: The selection type (Edit or Copy).
//     * @param position: Adapter position.
//     */
//    fun onTypeSelect(selType: Int, position: Int)

//    /**
//     * Get the Meeting Code of the item at position.
//     * @param ndx: A list index into the backing data (the position).
//     * @return The Meeting Code of the object at position.
//     */
//    fun getMeetingCode(ndx: Int): String

}