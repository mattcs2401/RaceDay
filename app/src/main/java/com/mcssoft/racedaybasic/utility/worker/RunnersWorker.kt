package com.mcssoft.racedaybasic.utility.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mcssoft.racedaybasic.data.repository.database.IDbRepo
import com.mcssoft.racedaybasic.data.repository.remote.IRemoteRepo
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class RunnersWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface IEntryPoints {
        fun getRemoteRepo(): IRemoteRepo
        fun getDbRepo(): IDbRepo
    }

    private val entryPoints = EntryPointAccessors.fromApplication(context, IEntryPoints::class.java)

    override suspend fun doWork(): Result {
        val date = inputData.getString("key_date")
//        Log.d("TAG", "[RunnerWorker.doWork] starting.")

//        try {
//            val iDbRepo = entryPoints.getDbRepo()
//            val codes = iDbRepo.getMeetingCodes()
//            val iRemoteRepo = entryPoints.getRemoteRepo()
//
//            // Loop through each of the meeting codes.
//            codes.forEach { code ->
//                // Get from the Api.
//                val baseDto = iRemoteRepo.getRaceDay(date!!, code)
//                // Get the MeetingDto, there'll only be one but a list is returned.
//                val meetingDto = baseDto.body.RaceDay.Meetings[0]
//
//                // Process the list of Races associated with the Meeting.
//
//                // Get the database row id of the equivalent Meeting in the database.
//                val mId = iDbRepo.getMeetingId(meetingDto.MeetingId)
//                // Loop through the list of RaceDto.
//                meetingDto.Races.forEach { raceDto ->
//                    // Get the (database row) of the Race.
//                    val rId = iDbRepo.getRaceId(mId, raceDto.RaceNumber)
//                    delay(50)  // TBA ?
//
//                    // Temporary list (used for database insert).
//                    val runners = arrayListOf<Runner>()
//                    // Create the (domain) Runners and add to listing.
//                    raceDto.Runners.forEach { runnerDto ->
//                        val runner = runnerDto.toRunner(rId)
//                        runners.add(runner)
//                    }
//                    // Insert the list of Runners.
//                    iDbRepo.insertRunners(runners)
//                    delay(50)  // TBA ?
//                }
//            }
//        } catch (exception: Exception) {
//            Log.d("TAG", "[RunnerWorker.doWork] exception.")
//            val key = context.resources.getString(R.string.key_exception)
//            val output = workDataOf(key to exception.localizedMessage)
//            return Result.failure(output)
//        }
//        Log.d("TAG", "[RunnerWorker.doWork] ending.")
        return Result.success()
    }
}