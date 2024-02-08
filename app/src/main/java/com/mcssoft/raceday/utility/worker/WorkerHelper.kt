package com.mcssoft.raceday.utility.worker

import android.content.Context
import com.mcssoft.raceday.R
import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.domain.model.Race
import com.mcssoft.raceday.domain.model.Runner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Class to process Runners if scratched, or associated with Trainer, Jockey or some Name.
 * @param context: For system resources.
 * @param iDbRepo: Database access.
 * @param race: The Race associated with the Runners.
 */
class WorkerHelper(
    private val context: Context,
    private val iDbRepo: IDbRepo,
    private val race: Race
) {
    // Get the list of Trainer, Jockey and Runner names.
    private val trainerNames = context.resources.getStringArray(R.array.trainerNames).toList()
    private val jockeyNames = context.resources.getStringArray(R.array.jockeyNames).toList()
    private val runnerNames = context.resources.getStringArray(R.array.runnerNamesPrefix).toList()

    init {
        CoroutineScope(SupervisorJob()).launch(Dispatchers.IO) {
            // Get the Runners for the Race.
            val lRunners = iDbRepo.getRunners(race.id)
            // Mark a Runner as Scratched.
            if (race.hasScratchings) {
                processForScratchings(race, lRunners)
            }
            // Get Runners associated by Trainer.
            processForTrainers(lRunners, trainerNames)
            // Get Runners associated by Jockey.
            processForJockeys(lRunners, jockeyNames)
            // Get Runners associated by Runner name.
            processForRunnerNames(lRunners, runnerNames)
        }
    }

    /**
     * Function to marry up the current list of scratchings (for a Race) with Runners and set them
     * as scratched.
     * @param race: The Race that has associated scratchings.
     */
    private suspend fun processForScratchings(race: Race, runners: List<Runner>) {
        // The list of scratchings.
        val lScratches = iDbRepo.getScratchingsForRace(race.venueMnemonic, race.raceNumber)

        runners.forEach { runner ->
            lScratches.forEach { scratch ->
                // Same name and runner number should be enough to be unique.
                if (scratch.runnerName == runner.runnerName &&
                    scratch.runnerNumber == runner.runnerNumber
                ) {
                    iDbRepo.updateRunnerAsScratched(runner.id, true)
                }
            }
        }
    }

    private suspend fun processForTrainers(runners: List<Runner>, trainerNames: List<String>) {
        runners.filter { runner ->
            !runner.isScratched && runner.trainerName in (trainerNames)
        }.also {
            for (runner in it) {
                iDbRepo.updateRunnerAsChecked(runner.id, true)
            }
        }
    }

    private suspend fun processForJockeys(runners: List<Runner>, jockeyNames: List<String>) {
        runners.filter { runner ->
            !runner.isScratched && runner.riderDriverName in (jockeyNames)
        }.also {
            for (runner in it) {
                iDbRepo.updateRunnerAsChecked(runner.id, true)
            }
        }
    }

    private suspend fun processForRunnerNames(runners: List<Runner>, runnerNames: List<String>) {
        runners.filter { runner ->
            !runner.isScratched
        }.also {
            for (name in runnerNames) {
                for (runner in it) {
                    if (runner.runnerName.startsWith(prefix = name, ignoreCase = true)) {
                        iDbRepo.updateRunnerAsChecked(runner.id, true)
                    }
                }
            }
        }
    }
}