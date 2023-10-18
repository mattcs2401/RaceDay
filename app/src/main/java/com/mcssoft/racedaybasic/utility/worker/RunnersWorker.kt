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
        Log.d("TAG", "[RunnerWorker.doWork] starting.")
        withContext(Dispatchers.IO) {
            try {
                val meetingIds = inputData.getStringArray("key_meeting_ids")
                val iDbRepo = entryPoints.getDbRepo()
                val iRemoteRepo = entryPoints.getRemoteRepo()

//                meetingIds?.forEach { id ->
//                    processForRunners(id, iRemoteRepo, iDbRepo)
//                }

                // Testing - just use the first value in the meetingIds array.
                processForRunners(meetingIds?.get(0) ?: "-1", iRemoteRepo, iDbRepo)


                //Loop through each of the meeting codes.
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
                Log.d("TAG", "[RunnerWorker.doWork] ending.")
                return@withContext Result.success()
            } catch (ex: Exception) {
                Log.d("TAG", "[RunnerWorker.doWork] exception: " + ex.toString())
                val key = context.resources.getString(R.string.key_exception)
                val output = workDataOf(key to ex.localizedMessage)

                return@withContext Result.failure(output)
            }
//            Log.d("TAG", "[RunnerWorker.doWork] ending.")
//            return@withContext Result.success()
        }
        return Result.success()
    }

    private suspend fun processForRunners(id: String, iRemoteRepo: IRemoteRepo, iDbRepo: IDbRepo) {
        val idList = id.split(":")
        val date = idList[0]
        val code = idList[1]
        val numRaces = idList[2]

        for(race in 1..(numRaces+1).toInt()) {

            val response = iRemoteRepo.getRaceDayRunners(date, code, race.toString())
            val raceIds = iDbRepo.getRaceIdsByVenueCode(code)
            iDbRepo.insertRunnersWithRaceId(raceIds, response.body.runners)

            val bp = ""
        }


    }

}