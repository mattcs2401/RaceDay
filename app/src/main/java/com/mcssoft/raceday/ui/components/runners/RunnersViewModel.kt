package com.mcssoft.raceday.ui.components.runners

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.data.repository.preferences.PrefsRepo
import com.mcssoft.raceday.domain.dto.SummaryDto
import com.mcssoft.raceday.domain.dto.toSummary
import com.mcssoft.raceday.domain.model.Race
import com.mcssoft.raceday.domain.model.Runner
import com.mcssoft.raceday.ui.components.runners.RunnersState.Status
import com.mcssoft.raceday.utility.Constants
import com.mcssoft.raceday.utility.Constants.TWENTY_FIVE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RunnersViewModel @Inject constructor(
    private val iDbRepo: IDbRepo,
    private val prefsRepo: PrefsRepo,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(RunnersState.initialise())
    val state = _state.asStateFlow()

    init {
        _state.update { state ->
            state.copy(
                status = Status.Initialise,
                fromApi = prefsRepo.fromApi
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            _state.emit(state.value)
        }
        /*
          The Runners screen expects a "raceId" (supplied in the navigation from the RacesScreen
          to the RunnersScreen).
         */
        savedStateHandle.get<Long>(Constants.KEY_RACE_ID)?.let { raceId ->
            // Get Race and Runner values for the screen.
            getRaceWithRunners(raceId)
        }
    }

    /**
     * Events raised from the UI.
     * @param event: The event type.
     */
    fun onEvent(event: RunnersEvent) {
        when (event) {
            is RunnersEvent.Check -> {
                setRunnerChecked(event.race, event.runner)
            }
        }
    }

    private fun getRaceWithRunners(raceId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = iDbRepo.getRaceWithRunners(raceId)

                // The keys are a Set with only one value (the Race object itself).
                val race = result.keys.elementAt(0)
                var runners = result.getValue(race)

                runners = processScratchings(runners)

                delay(TWENTY_FIVE) // TBA

                _state.update { state ->
                    state.copy(
                        status = Status.Success,
                        race = race,
                        lRunners = runners
                    )
                }
                _state.emit(state.value)
            } catch(ex: Exception) {
                _state.update { state ->
                    state.copy(
                        status = Status.Failure
                    )
                }
                _state.emit(state.value)
            }

        }
    }

    /**
     * Return a list of Runners where scratchings are at the bottom of the list.
     * @param runners: The list to be processed.
     * @return A list of Runners sorted with scratchings at the end.
     */
    private fun processScratchings(runners: List<Runner>): List<Runner> {
        val lTemp = mutableListOf<Runner>()
        runners.partition { runner ->
            runner.isScratched
        }.also { pair ->
            lTemp.addAll(pair.second.sortedBy { it.runnerNumber })
            lTemp.addAll(pair.first.sortedBy { it.runnerNumber })
        }
        return lTemp
    }

    /**
     * Set the metadata 'checked' on the Runner object.
     * @param runner: The Runner.
     */
    private fun setRunnerChecked(race: Race, runner: Runner) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                updateRunnerChecked(race, runner)
                delay(TWENTY_FIVE)
                _state.update { state ->
                    state.copy(
                        exception = null,
                        status = Status.Success
                    )
                }
                _state.emit(state.value)
            } catch(ex: Exception) {
                _state.update { state ->
                    state.copy(
                        exception = ex,
                        status = Status.Failure
                    )
                }
                _state.emit(state.value)
            }
        }
    }

    private suspend fun updateRunnerChecked(race: Race, runner: Runner) {
        // Update the isChecked value for the Runner.
        // Note: could be going from false -> true, or, true -> false.
        iDbRepo.updateRunnerAsChecked(runner.id, runner.isChecked)

        if (runner.isChecked) {
            // Create the associated Summary record.
            SummaryDto(
                race.id,
                runner.id,
                race.sellCode,
                race.venueMnemonic,
                race.raceNumber,
                race.raceStartTime,
                runner.runnerNumber,
                runner.runnerName,
                runner.riderDriverName,
                runner.trainerName
            )
            .toSummary()
            .let { summary ->
                iDbRepo.insertSummary(summary)
            }
        } else {
            // Was checked, now unchecked, so remove Summary item.
            iDbRepo.getSummary(race.id, runner.id).let { summary ->
                iDbRepo.deleteSummary(summary.id)
            }
        }
    }
}