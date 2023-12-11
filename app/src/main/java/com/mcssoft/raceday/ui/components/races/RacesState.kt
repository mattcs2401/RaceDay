package com.mcssoft.raceday.ui.components.races

import com.mcssoft.raceday.domain.model.Meeting
import com.mcssoft.raceday.domain.model.Race

/**
 * Class to hold the state of the RacesScreen.
 * @param exception: An exception (if thrown).
 * @param status: Loading, Failure, Success.
 * @param loading: Data is being collected.
 * @param lRaces: The list of Races to display (associated with the Meeting).
 * @param mtg: The Meeting associated with the Races listing (used for listing header info).
 */
data class RacesState(
    val exception: Exception?,
    val status: Status = Status.NoState,
    val loading: Boolean = false,
    val lRaces: List<Race> = emptyList(),
    val mtg: Meeting?) {

    companion object {
        fun initialise(): RacesState {
            return RacesState(
                exception = null,
                status = Status.Initialise,
                mtg = null,
            )
        }
    }

    sealed class Status {
        data object Initialise : Status()
        data object Loading : Status()
        data object Success : Status()
        data object Failure : Status()
        data object NoState: Status()
    }

    val races: List<Race>
        get() = this.lRaces

    val meeting: Meeting?
        get() = this.mtg
}