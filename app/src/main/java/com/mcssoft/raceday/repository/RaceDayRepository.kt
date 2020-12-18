package com.mcssoft.raceday.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.mcssoft.raceday.database.RaceDay
import com.mcssoft.raceday.database.entity.RaceMeeting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class RaceDayRepository @Inject constructor(context: Context) {

    private val completableJob = Job()

    private val coroutineScope =
            CoroutineScope(Dispatchers.IO + completableJob)

    private val raceDetailsDAO = RaceDay.getDatabase(context.applicationContext as Application).raceDayDetailsDao()

    private var raceDayCache: LiveData<List<RaceMeeting>>? = null
    //= liveData {
    //    emitSource(raceDetailsDAO.getMeetings())
    //}

//    /**
//     * Delete all from the race_day_details table.
//     * @note: Must be called on a background thread.
//     */
//    fun deleteAll() = raceDetailsDAO.deleteAll()

    /**
     * Insert a RaceDetails (entity) meeting.
     * @param meeting: The meeting to insert.
     * @note: Must be called on a background thread.
     */
    fun insertMeeting(meeting: RaceMeeting) {
        coroutineScope.launch(Dispatchers.IO) {
            raceDetailsDAO.insertMeeting(meeting)
            raceDayCache = raceDetailsDAO.getMeetings()
        }
    }

    /**
     * Create the cache that will be used by the ViewModel.
     */
    fun createOrRefreshCache() {
        coroutineScope.launch(Dispatchers.IO) {
            raceDayCache = raceDetailsDAO.getMeetings()
        }
    }

    fun getRaceDayCache(): LiveData<List<RaceMeeting>>? = raceDayCache

    fun clearCache() {
        coroutineScope.launch(Dispatchers.IO) {
            raceDetailsDAO.deleteAll()
            raceDayCache = null
        }
    }

    fun haveCache(): Boolean {
        return raceDayCache != null
    }

    //<editor-fold default state="collapsed" desc="Region: XXX">
    //</editor-fold>

}

/*
FYI
https://vladsonkin.com/android-coroutine-scopes-how-to-handle-a-coroutine/?utm_source=feedly&utm_medium=rss&utm_campaign=android-coroutine-scopes-how-to-handle-a-coroutine
 */
/*
    val errorHandler = CoroutineExceptionHandler { _, exception ->
      AlertDialog.Builder(this).setTitle("Error")
              .setMessage(exception.message)
              .setPositiveButton(android.R.string.ok) { _, _ -> }
              .setIcon(android.R.drawable.ic_dialog_alert).show()
    }
 */