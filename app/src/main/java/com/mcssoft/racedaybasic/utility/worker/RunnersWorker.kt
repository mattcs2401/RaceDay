package com.mcssoft.racedaybasic.utility.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.mcssoft.racedaybasic.R
import com.mcssoft.racedaybasic.data.repository.database.IDbRepo
import com.mcssoft.racedaybasic.data.repository.remote.IRemoteRepo
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
    private val entryPoints = EntryPointAccessors.fromApplication(context, IEntryPoint::class.java)

    override suspend fun doWork(): Result {
        return try {
            Log.d("TAG", "[RunnerWorker.doWork] starting.")

            val date = inputData.getString("key_meeting_date")
            val code = inputData.getString("key_meeting_code")
            val races = inputData.getString("key_num_races")

            val iDbRepo = entryPoints.getDbRepo()
            val iRemoteRepo = entryPoints.getRemoteRepo()

            processForRunners(date!!, code!!, races!!, iRemoteRepo, iDbRepo)

            Result.success()
        } catch(ex: Exception) {
            Log.d("TAG", "[RunnerWorker.doWork] exception: " + ex.toString())
            val key = context.resources.getString(R.string.key_exception)
            val output = workDataOf(key to ex.localizedMessage)

            Result.failure(output)
        }

    }

    private suspend fun processForRunners(date: String, code: String, races: String, iRemoteRepo: IRemoteRepo, iDbRepo: IDbRepo) {
        for(race in 1..races.toInt()) {
            Log.d("TAG", "Process for Runners - Race: $race")
            val response = iRemoteRepo.getRaceDayRunners(date, code, race.toString())
            val raceIds = iDbRepo.getRaceIdsByVenueCode(code)
            iDbRepo.insertRunnersWithRaceId(raceIds, response.body.runners)
        }
    }

}