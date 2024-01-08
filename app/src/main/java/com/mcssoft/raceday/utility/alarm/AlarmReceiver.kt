package com.mcssoft.raceday.utility.alarm

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.domain.model.Summary
import com.mcssoft.raceday.utility.DateUtils
import com.mcssoft.raceday.utility.notification.INotification
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class AlarmReceiver: BroadcastReceiver() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface IEntryPoints {
        fun notificationManager(): INotification
        fun notificationBuilder(): INotification
        fun dbAccess(): IDbRepo
    }

    private lateinit var notificationManager: NotificationManagerCompat
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var dataAccess: IDbRepo

    // TODO - how do we determine which Summary items require a Notification ?

    @SuppressLint("MissingPermission") // Permission is in the manifest, but still complains.
    override fun onReceive(context: Context, intent: Intent?) {
//        Log.d("TAG", "BroadcastReceiver.onReceive")

//        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return

        setEntryPoints(context)

        goAsync {
            processReceive()
        }
    }

    private suspend fun processReceive() {//pendingResult: PendingResult, context: Context) {
        Log.d("TAG","AlarmReceiver.processReceive()")

        // The current time in millis.
        val currentTimeMillis = DateUtils().getCurrentTimeMillis()
        // List of Summaries that are notification candidates.
        val notifyList = mutableListOf<Summary>()
        // Check for any Summaries where the race time is now in the past and update.
        checkSummaries(currentTimeMillis)
        // A time "window" of the current time -> (current time + five minutes).
        val windowMaxTime = currentTimeMillis + (1000 * 60 * 5).toLong()
        // Get a list of Summaries whose race time is still in the future.
        val baseNotifyList = dataAccess.getCurrentSummaries()
        // Create a list of Summaries whose race time is within the time window.
        for (summary in baseNotifyList) {
            val result = DateUtils().compareToWindowTime(summary.raceStartTime, windowMaxTime)
            if(result == 1) {
                notifyList.add(summary)
            }
        }

        // TODO - build and send the notifications.
//        notificationManager.notify(1, notificationBuilder.build())
    }

    /**
     * Check for Summaries whose race time is now in the past, and update.
     * @param currentTime: The current time in millis.
     */
    private suspend fun checkSummaries(currentTime: Long) {
        var raceTime: Long

        val lSummaries = dataAccess.getSummaries()

        for(summary in lSummaries) {
            raceTime = DateUtils().getCurrentTimeMillis(summary.raceStartTime)

            if(!summary.isPastRaceTime) {
                if(currentTime > raceTime) {
                    summary.isPastRaceTime = true

                    dataAccess.updateSummary(summary)
                }
            }
        }
    }

    /**
     * Setup the entry point accessors.
     */
    private fun setEntryPoints(context: Context) {
        val entryPoints =
            EntryPointAccessors.fromApplication(context, IEntryPoints::class.java)
        notificationManager = entryPoints.notificationManager().getNotificationManager()

        notificationBuilder =
            entryPoints.notificationBuilder()
                .getNotificationBuilder()

        dataAccess = entryPoints.dbAccess()
    }
}
private fun BroadcastReceiver.goAsync(
    context: CoroutineContext = EmptyCoroutineContext,
    codeBlock: suspend CoroutineScope.() -> Unit
) {
    val pendingResult = goAsync()
    CoroutineScope(SupervisorJob()).launch(context) {
        try {
            codeBlock()
        } catch(ex: Exception) {
            Log.e("TAG","Exception in BroadcastReceiver.goAsync: ${ex.message}")
        } finally {
            pendingResult.finish()
        }
    }
}
// Ref: https://stackoverflow.com/questions/22741202/how-to-use-goasync-for-broadcastreceiver
