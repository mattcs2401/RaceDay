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
import com.mcssoft.raceday.utility.Constants.TWENTY_FIVE
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.delay

/**
 * Class to insert the Runners associated with a Race.
 * @param context: App context for EntryPoint.
 * @param workerParams: Input data for meeting date, code and number of Races.
 */
class RunnersWorker(
    private val context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    // <editor-fold default state="collapsed" desc="Entry points.">
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
    // </editor-fold>

    override suspend fun doWork(): Result {
        return try {
            var race: Race
            var response: NetworkResponse<BaseDto2> // response from Api.

            val meetingDate = inputData.getString(context.resources.getString(R.string.key_meeting_date))
            val meetingCode = inputData.getString(context.resources.getString(R.string.key_meeting_code)) // venue mnemonic.
            val numRaces = inputData.getString(context.resources.getString(R.string.key_num_races))

            for (raceNum in 1..(numRaces?.toInt() ?: -1)) {
                // Get the Race. Need Race info to get Scratchings.
                race = iDbRepo.getRaceByVenueCodeAndRaceNo(meetingCode!!, raceNum)

                // If the Race is already Abandoned, don't waste resources getting Runner detail.
                if (race.raceStatus != context.resources.getString(R.string.race_status_abandoned)) {

                    // Get the Runners for a Race. Parameter raceNum as string for Url construct.
                    response = iRemoteRepo.getRaceDayRunners(meetingDate!!, meetingCode, raceNum.toString())

                    // Insert records.
                    iDbRepo.insertRunnersWithRaceId(race.id, response.body.runners)

                    delay(TWENTY_FIVE) // TBA.

                    // Do the heavy lifting.
                    WorkerHelper(context, iDbRepo, race)

                    delay(TWENTY_FIVE) // TBA.
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
}
