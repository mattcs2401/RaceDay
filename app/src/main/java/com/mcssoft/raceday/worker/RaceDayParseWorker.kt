package com.mcssoft.raceday.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mcssoft.raceday.R
import com.mcssoft.raceday.database.entity.RaceMeeting
import com.mcssoft.raceday.events.ResultMessageEvent
import com.mcssoft.raceday.repository.RaceDayRepository
import com.mcssoft.raceday.utility.Constants
import com.mcssoft.raceday.utility.RaceDayParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus

class RaceDayParseWorker(private val context: Context, private val params: WorkerParameters):
        CoroutineWorker(context, params) {

    private lateinit var raceDayParser: RaceDayParser
    private lateinit var raceDayRepository: RaceDayRepository

    // Result list of the Xml parsing.
    private var meetingsListing = ArrayList<MutableMap<String, String>>()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {

            val fileId = params.inputData.getLong(context.resources.getString(R.string.key_file_id), Constants.MINUS_ONE_L)

            // Initialise parser.
            raceDayParser = RaceDayParser(context)
            raceDayParser.setInputStream(fileId)

            // Get the list of meetings.
            meetingsListing = raceDayParser.parseForMeeting()

            // Instantiate repository (for database access).
            raceDayRepository = RaceDayRepository(context)

            // Delete anything previously there.
            raceDayRepository.clearCache()

            // Write the new details.
            for (item in meetingsListing) {
                val meeting = RaceMeeting(item["MtgId"]!!)

                meeting.weatherChanged = item["WeatherChanged"]!!
                meeting.meetingCode = item["MeetingCode"]!!
                meeting.venueName = item["VenueName"]!!
                meeting.hiRaceNo = item["HiRaceNo"]!!
                meeting.meetingType = item["MeetingType"]!!
                meeting.trackChanged = item["TrackChanged"]!!
                meeting.nextRaceNo = item["NextRaceNo"].toString()   // may not exist.
                meeting.sortOrder = item["SortOrder"]!!
                meeting.abandoned = item["Abandoned"]!!

                raceDayRepository.insertMeeting(meeting)
            }

            Log.d("TAG", "ParseWorker - Result.success")

            // Notify in Fragment.
            EventBus.getDefault().post(ResultMessageEvent(Constants.RESULT_SUCCESS))

            Result.success()

        } catch (error: Throwable) {
            // TODO - more meaningful errors, maybe a dialog ? Notify ?

            Log.d("TAG", "ParseWorker - Result.failure")
            EventBus.getDefault().post(ResultMessageEvent(Constants.RESULT_FAILURE))

            Result.failure()
        }
    }

}
