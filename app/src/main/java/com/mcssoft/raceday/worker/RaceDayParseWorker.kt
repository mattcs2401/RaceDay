package com.mcssoft.raceday.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mcssoft.raceday.R
import com.mcssoft.raceday.database.entity.RaceMeeting
import com.mcssoft.raceday.repository.RaceDayRepository
import com.mcssoft.raceday.utility.Constants
import com.mcssoft.raceday.utility.RaceDayParser
import com.mcssoft.raceday.utility.RaceDownloadManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xml.sax.SAXParseException
import javax.inject.Inject

class RaceDayParseWorker(private val context: Context, private val params: WorkerParameters): CoroutineWorker(context, params) {

    private lateinit var raceDayParser: RaceDayParser
    private lateinit var raceDayRepository: RaceDayRepository

    // Result list of the Xml parsing.
    private var meetingsListing = ArrayList<MutableMap<String, String>>()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {

            val fileId = params.inputData.getLong(
                context.resources.getString(R.string.key_file_id),
                Constants.MINUS_ONE_L
            )

            // Initialise parser.
            raceDayParser = RaceDayParser(context)
            raceDayParser.setInputStream(fileId)

            // Get the list of meetings.
            meetingsListing = raceDayParser.parseForMeeting()

            // Instantiate repository (for database access).
            raceDayRepository = RaceDayRepository(context)

            // Delete anything previously there.
            raceDayRepository.raceDetailsDAO.deleteAll()

            // Write the new details.
            for (item in meetingsListing) {
                val meeting = RaceMeeting(item["MtgId"]!!)

                meeting.weatherChanged = item["WeatherChanged"]!!
                meeting.meetingCode = item["MeetingCode"]!!
                meeting.venueName = item["VenueName"]!!
                meeting.hiRaceNo = item["HiRaceNo"]!!
                meeting.meetingType = item["MeetingType"]!!
                meeting.trackChanged = item["TrackChanged"]!!
                meeting.nextRaceNo = item["NextRaceNo"].toString()   // empty string if null.
                meeting.sortOrder = item["SortOrder"]!!
                meeting.abandoned = item["Abandoned"]!!

                Log.d("TAG", "meetingId=" + meeting.mtgId)
                raceDayRepository.raceDetailsDAO.insertMeeting(meeting)
            }

            Log.d("TAG", "ParseWorker - Result.success")
            Result.success()

            // TBA - this is the end point of the file download and parse.
            // File has been downloaded by DownLoadManager, a broadcast sent and picked up by the
            // DownloadReceiver. Receiver has spawned a this Worker to parse and write the file
            // data in the background.

            // TODO - notify and stop spinner in SplashFragment, navigate to MainFragment.

        } catch (error: Throwable) {
            // TODO - more meaningful errors, maybe a dialog ? Notify ?
            Log.d("TAG", "ParseWorker - Result.failure")
            Result.failure()
        }
    }

}
