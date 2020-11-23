package com.mcssoft.raceday.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mcssoft.raceday.R
import com.mcssoft.raceday.repository.RaceDayRepository
import com.mcssoft.raceday.utility.Constants
import com.mcssoft.raceday.utility.RaceDayParser
import com.mcssoft.raceday.utility.RaceDownloadManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RaceDayParseWorker(private val context: Context, private val params: WorkerParameters): CoroutineWorker(context, params) {

    private lateinit var raceDayParser: RaceDayParser
    private lateinit var raceDayRepository: RaceDayRepository

    // Result list of the Xml parsing.
    private lateinit var meetingsListing: ArrayList<MutableMap<String, String>>

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {

            val fileId = params.inputData.getLong(context.resources.getString(R.string.key_file_id), Constants.MINUS_ONE_L)

            raceDayParser = RaceDayParser(context)
            raceDayParser.setInputStream(fileId)

            meetingsListing = raceDayParser.parseForMeeting()

            raceDayRepository = RaceDayRepository(context)

            // TODO - write to database.
            // TODO - notify ? stop spinner in SplashFragment, navigate to MainFragment.

            Result.success()

        } catch (error: Throwable) {
            Result.failure()
        }
    }

}