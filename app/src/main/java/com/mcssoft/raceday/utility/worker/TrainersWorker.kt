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
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

/**
 * Class to get Trainers, and associated Race/Runner details.
 * @param context: App context for EntryPoint.
 * @param workerParams:
 */
class TrainersWorker (
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
            Log.d("TAG", "[TrainersWorker.doWork] starting.")

            Result.success()
        } catch(ex: Exception) {
            Log.d("TAG", "[TrainersWorker.doWork] exception: $ex")
            val key = context.resources.getString(R.string.key_exception)
            val output = workDataOf(key to ex.localizedMessage)

            Result.failure(output)
        }
    }

}
