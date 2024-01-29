package com.mcssoft.raceday.utility.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.mcssoft.raceday.R
import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.data.repository.remote.IRemoteRepo
import com.mcssoft.raceday.data.repository.remote.NetworkResponse
import com.mcssoft.raceday.domain.dto.BaseDto2
import com.mcssoft.raceday.domain.model.Race
import com.mcssoft.raceday.domain.model.Runner
import com.mcssoft.raceday.utility.Constants.TWENTY_FIVE
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.delay

/**
 * Class to insert the Runners associated with a Race, and get Trainers associated with the Runners.
 * @param context: App context for EntryPoint.
 * @param workerParams:
 */
class RunnersWorker(
    private val context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface IEntryPoint {
        fun getRemoteRepo(): IRemoteRepo
        fun getDbRepo(): IDbRepo
    }
    private val entryPoints =
        EntryPointAccessors.fromApplication(context, IEntryPoint::class.java)
    private val iDbRepo = entryPoints.getDbRepo()
    private val iRemoteRepo = entryPoints.getRemoteRepo()

    override suspend fun doWork(): Result {
        return try {
//            Log.d("TAG", "[RunnersWorker.doWork] starting.")
            // Get the list of Trainer, Jockey and Runner names.
            val trainerNames = context.resources.getStringArray(R.array.trainerNames).toList()
            val jockeyNames = context.resources.getStringArray(R.array.jockeyNames).toList()
            val runnerNames = context.resources.getStringArray(R.array.runnerNamesPrefix).toList()

            var race: Race
            var raceId: Long
            var response: NetworkResponse<BaseDto2> // response from Api.

            val date = inputData.getString(context.resources.getString(R.string.key_meeting_date))
            val code = inputData.getString(context.resources.getString(R.string.key_meeting_code))
            val races = inputData.getString(context.resources.getString(R.string.key_num_races))

            for (raceNum in 1..(races?.toInt() ?: -1)) {
                // Get the Race id for the Race.
                raceId = iDbRepo.getRaceIdByVenueCodeAndRaceNo(code!!, raceNum)

                // Get the Race. Need Race info to get Scratchings.
                race = iDbRepo.getRace(raceId)

                // If the Race is Abandoned, don't waste resources getting Runner detail.
                if (race.raceStatus != context.resources.getString(R.string.race_status_abandoned)) {
                    // Get the Runners for a Race. Parameter raceNum as string for Url construct.
                    response = iRemoteRepo.getRaceDayRunners(date!!, code, raceNum.toString())
                    // Insert records.
                    iDbRepo.insertRunnersWithRaceId(raceId, response.body.runners)
                    delay(TWENTY_FIVE) // TBA.
                    // Get the current lis of Runners for the Race.
                    val lRunners = iDbRepo.getRunners(race._id)

                    if (race.hasScratchings) {
                        processForScratchings(race, lRunners)
                    }

                    delay(TWENTY_FIVE) // TBA required ?

                    processForTrainers(lRunners, trainerNames)
                    processForJockeys(lRunners, jockeyNames)
                    processForRunnerNames(lRunners, runnerNames)
                }
            }
            Result.success()
        } catch (ex: Exception) {
            Log.d("TAG", "[RunnersWorker.doWork] exception: $ex")
            val key = context.resources.getString(R.string.key_exception)
            val output = workDataOf(key to ex.localizedMessage)

            Result.failure(output)
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
                    runner.isScratched = true
                    iDbRepo.updateRunnerAsScratched(runner)
                }
            }
        }
    }

    private suspend fun processForTrainers(runners: List<Runner>, trainerNames: List<String>) {
        runners.filter { runner ->
            !runner.isScratched && runner.trainerName in (trainerNames)
        }.also {
            for (runner in it) {
                iDbRepo.updateRunnerChecked(runner._id, true)
            }
        }
    }

    private suspend fun processForJockeys(runners: List<Runner>, jockeyNames: List<String>) {
        runners.filter { runner ->
            !runner.isScratched && runner.riderDriverName in (jockeyNames)
        }.also {
            for (runner in it) {
                iDbRepo.updateRunnerChecked(runner._id, true)
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
                        iDbRepo.updateRunnerChecked(runner._id, true)
                    }
                }
            }
        }
    }
}
