package com.mcssoft.raceday.ui.components.races

import com.mcssoft.raceday.domain.model.Meeting
import com.mcssoft.raceday.domain.model.Race

/**
 * Class to hold the state of the RacesScreen.
 * @param exception: An exception (if thrown).
 * @param status: Loading, Failure, Success.
 * @param lRaces: The list of Races to display (associated with the Meeting).
 * @param meeting: The Meeting associated with the Races listing (used for listing header info).
 */
data class RacesState(
    val exception: Exception?,
    val status: Status?,
    val lRaces: List<Race>?,
    val meeting: Meeting?,
    val fromApi: Boolean
) {
    companion object {
        fun initialise(): RacesState {
            return RacesState(
                exception = null,
                status = Status.Initialise,
                lRaces = emptyList(),
                meeting = null,
                fromApi = false
            )
        }
    }

    sealed class Status {
        data object Initialise : Status()
        data object Success : Status()
        data object Failure : Status()
    }

    val races: List<Race>
        get() = lRaces ?: emptyList()
}