package com.mcssoft.raceday.repository

import android.app.Application
import android.content.Context
import com.mcssoft.raceday.database.RaceDay
import com.mcssoft.raceday.database.dao.IRaceDayDAO
import kotlinx.coroutines.*
import javax.inject.Inject

class RaceDayRepository @Inject constructor(private val context: Context) {

    val completableJob = Job()

    private val coroutineScope =
            CoroutineScope(Dispatchers.IO + completableJob)

    val raceDetailsDAO = RaceDay.getDatabase(context.applicationContext as Application).raceDayDetailsDao()

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