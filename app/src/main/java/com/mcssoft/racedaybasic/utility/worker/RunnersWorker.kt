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

            val date = inputData.getString(context.resources.getString(R.string.key_meeting_date))
            val code = inputData.getString(context.resources.getString(R.string.key_meeting_code))
            val races = inputData.getString(context.resources.getString(R.string.key_num_races))

            for(race in 1..(races?.toInt() ?: -1)) {
                Log.d("TAG", "Process for Runners - Race: $race")

                // Get the Runners for a Race.
                response = iRemoteRepo.getRaceDayRunners(date!!, code!!, race.toString())
                // Get the Race id for the Race.
                raceId = iDbRepo.getRaceIdByVenueCodeAndRaceNo(code, race.toString())
                // Insert records.
                iDbRepo.insertRunnersWithRaceId(raceId, response.body.runners)
            }

            Result.success()
        } catch(ex: Exception) {
            Log.d("TAG", "[RunnerWorker.doWork] exception: " + ex.toString())
            val key = context.resources.getString(R.string.key_exception)
            val output = workDataOf(key to ex.localizedMessage)

            Result.failure(output)
        }
    }

}