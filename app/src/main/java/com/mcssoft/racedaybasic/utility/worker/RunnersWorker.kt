package com.mcssoft.racedaybasic.utility.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.mcssoft.racedaybasic.R
import com.mcssoft.racedaybasic.data.repository.database.IDbRepo
import com.mcssoft.racedaybasic.data.repository.remote.IRemoteRepo
import com.mcssoft.racedaybasic.data.repository.remote.NetworkResponse
import com.mcssoft.racedaybasic.domain.dto.BaseDto2
import com.mcssoft.racedaybasic.domain.model.Race
import com.mcssoft.racedaybasic.domain.model.Runner
import com.mcssoft.racedaybasic.domain.model.Scratching
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

/**
 * Class to insert the Runners associated with a Race.
 */
class RunnersWorker (
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
            Log.d("TAG", "[RunnerWorker.doWork] starting.")

            var response: NetworkResponse<BaseDto2>   // response from Api.
            var  raceId: Long
            var race: Race

            val date = inputData.getString(context.resources.getString(R.string.key_meeting_date))
            val code = inputData.getString(context.resources.getString(R.string.key_meeting_code))
            val races = inputData.getString(context.resources.getString(R.string.key_num_races))

            for(raceNum in 1..(races?.toInt() ?: -1)) {
                Log.d("TAG", "Process for Runners - Race: $raceNum")

                // Get the Runners for a Race. Parameter raceNum as sString for Url construct.
                response = iRemoteRepo.getRaceDayRunners(date!!, code!!, raceNum.toString())
                // Get the Race id for the Race.
                raceId = iDbRepo.getRaceIdByVenueCodeAndRaceNo(code, raceNum)
                // Insert records.
                iDbRepo.insertRunnersWithRaceId(raceId, response.body.runners)
                // Get the Race. Need Race info to get Scratchings.
                race = iDbRepo.getRace(raceId)

                if(race.hasScratchings) {
                    processScratchings(race)
                }
            }

            Result.success()
        } catch(ex: Exception) {
            Log.d("TAG", "[RunnerWorker.doWork] exception: " + ex.toString())
            val key = context.resources.getString(R.string.key_exception)
            val output = workDataOf(key to ex.localizedMessage)

            Result.failure(output)
        }
    }

    private suspend fun processScratchings(race: Race) {
        val runners = iDbRepo.getRunners(race._id)
        val scratches = iDbRepo.getScratchingsForRace(race.venueMnemonic, race.raceNumber)

        val sRunner = mutableListOf<Runner>()    // scratched Runners.

        runners.forEach { runner ->
            scratches.forEach { scratch ->
                if(scratch.runnerName == runner.runnerName &&
                    scratch.runnerNumber == runner.runnerNumber
                ) {
                    runner.isScratched = true
                    sRunner.add(runner)
                }
            }
        }
        iDbRepo.updateRunnersAsScratched(sRunner)
        // TODO - Update the Runner records in the database.
       val bp = "bp"
    }
}
/*
fun <T, U> List<T>.myEquals(other: List<T>, compareBy: (T) -> U): Boolean {
    return map(compareBy).toSet() == other.map(compareBy).toSet()
}
 */