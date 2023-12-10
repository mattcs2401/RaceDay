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
 * @param mtgId: The meeting id of the associated meeting. Kept separate as it's used to retrieve
 *               the Meeting on back nav to the Races screen.
 */
data class RacesState(
    val exception: Exception?,
    val status: Status = Status.NoState,
    val loading: Boolean = false,
    val lRaces: List<Race> = emptyList(),
    val mtg: Meeting?,
    var mtgId: Long = 0
) {
    companion object {
        fun initialise(): RacesState {
            return RacesState(
                exception = null,
                status = Status.Initialise,
                mtg = null,
            )
        }
//        fun loading(): RacesState {
//            return RacesState(
//                exception = null,
//                status = Status.Loading,
//                mtg = null,
//                // TBA mtg ?
//            )
//        }
//        fun failure(): RacesState {
//
//        }
//        fun succes(): RacesState {
//
//        }
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