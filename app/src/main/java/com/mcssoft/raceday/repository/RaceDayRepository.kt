package com.mcssoft.raceday.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mcssoft.raceday.database.RaceDay
import com.mcssoft.raceday.database.entity.RaceMeeting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RaceDayRepository @Inject constructor(context: Context) {

    private val completableJob = Job()

    private val coroutineScope =
            CoroutineScope(Dispatchers.IO + completableJob)

    private val raceDetailsDAO = RaceDay.getDatabase(context.applicationContext as Application).raceDayDetailsDao()

    private var raceDayCache: LiveData<List<RaceMeeting>>? = null

    /**
     * Create the cache that will be used by the ViewModel.
     */
    fun createOrRefreshCache() {
        coroutineScope.launch(Dispatchers.IO) {
            raceDayCache = raceDetailsDAO.getMeetings()
        }
    }

    fun getRaceDayCache(): LiveData<List<RaceMeeting>>? = raceDayCache

    /**
     * Insert a RaceDetails (entity) meeting.
     * @param meeting: The meeting to insert.
     */
    fun insertMeeting(meeting: RaceMeeting) {
        coroutineScope.launch(Dispatchers.IO) {
            raceDetailsDAO.insertMeeting(meeting)
            .also {
                // refresh cache.
                raceDayCache = raceDetailsDAO.getMeetings()
            }
        }
    }

    fun clearCache() {
        coroutineScope.launch(Dispatchers.IO) {
            raceDetailsDAO.deleteAll()
            raceDayCache = null
        }
    }

//    fun haveCache(): Boolean {
//        return raceDayCache != null
//    }

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