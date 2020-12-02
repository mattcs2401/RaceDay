package com.mcssoft.raceday.repository

import android.app.Application
import android.content.Context
import com.mcssoft.raceday.database.RaceDay
import com.mcssoft.raceday.database.dao.IRaceDayDAO
import com.mcssoft.raceday.database.entity.RaceMeeting
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class RaceDayRepository @Inject constructor(private val context: Context) {

    val completableJob = Job()

    private val coroutineScope =
            CoroutineScope(Dispatchers.IO + completableJob)

    private val raceDetailsDAO = RaceDay.getDatabase(context.applicationContext as Application).raceDayDetailsDao()

    private var _raceDayCache: Flow<List<RaceMeeting>>? = null
    val raceDayCache: Flow<List<RaceMeeting>>
        get() {
            if(_raceDayCache == null) {
                _raceDayCache = raceDetailsDAO.getMeetings()
            }
            return _raceDayCache as Flow<List<RaceMeeting>>
    }

    /**
     * Delete all from the race_day_details table.
     * @note: Must be called on a background thread.
     */
    fun deleteAll() = raceDetailsDAO.deleteAll()

    /**
     * Insert a RaceDetails (entity) meeting.
     * @param meeting: The meeting to insert.
     * @note: Must be called on a background thread.
     */
    fun insertMeeting(meeting: RaceMeeting) = raceDetailsDAO.insertMeeting(meeting)

    /**
     * Create the cache that will be used by the ViewModel.
     */
    fun createCache() {
        _raceDayCache = raceDetailsDAO.getMeetings()
    }

    fun getCacheCount(): Int {
        var count = -1;
        coroutineScope.launch {
            count = _raceDayCache?.count()!!
        }
        return count
    }

    fun getAt(ndx: Int): RaceMeeting {
        var raceMeeting = RaceMeeting("")
        coroutineScope.launch {
            raceMeeting = raceDayCache.toList()[0][ndx]// .get(ndx)
        }
        return raceMeeting
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