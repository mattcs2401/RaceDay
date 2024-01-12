package com.mcssoft.raceday.utility.alarm

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mcssoft.raceday.R
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
import kotlinx.coroutines.delay
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

    override fun onReceive(context: Context, intent: Intent?) {
//        Log.d("TAG", "BroadcastReceiver.onReceive")
//        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return

        setEntryPoints(context)

        goAsync {
            processReceive(context)
        }
    }

    private suspend fun processReceive(context: Context) {//pendingResult: PendingResult, context: Context) {
        Log.d("TAG","AlarmReceiver.processReceive()")
        // The current time in millis.
        val currentTime = DateUtils().getCurrentTimeMillis()
        // List of Summaries that are notification candidates.
        val notifyList = mutableListOf<Summary>()
        // Check for any Summaries where the race time is now in the past and update accordingly.
        checkSummaries(currentTime)
        // A time "window" of the current time -> (current time + five minutes).
        val windowTime = currentTime + (1000 * 60 * 5).toLong()
        // Get a list of Summaries whose race time is still in the future.
        delay(25) // TBA ?
        val baseNotifyList = dataAccess.getCurrentSummaries()
        delay(25) // TBA
        // Create a list of Summaries whose race time is within the time window.
        if(baseNotifyList.isEmpty()) return else for (summary in baseNotifyList) {
            if(DateUtils().compareToWindowTime(summary.raceStartTime, windowTime)) {
                notifyList.add(summary)
            }
        }
        if(notifyList.isEmpty()) return else for (summary in notifyList) {
            sendNotification(context, summary)
        }
    }

    @SuppressLint("MissingPermission") // Permission is in the manifest, but still complains.
    private fun sendNotification(context: Context, summary: Summary) {
        val bundle = Bundle().also {
            it.putLong("key_summary_id", summary._id)
        }
        val view = buildView(context, summary)
        notificationBuilder.also { ncb ->
            ncb.setCustomContentView(view)
            ncb.setExtras(bundle)
        }
        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }

    /**
     * Build the Notification's view from Summary details.
     * @param context: For system string resources.
     * @param summary: The Summary to draw data from.
     */
    private fun buildView(context: Context, summary: Summary): RemoteViews {
        return RemoteViews(context.packageName, R.layout.layout_notification).also { rvs ->
            rvs.setTextViewText(R.id.id_sellCode, summary.sellCode)
            rvs.setTextViewText(R.id.id_raceTime, summary.raceStartTime)
            rvs.setTextViewText(R.id.id_raceNumber, summary.raceNumber.toString())
            rvs.setTextViewText(R.id.id_runnerNumber, summary.runnerNumber.toString())
            rvs.setTextViewText(R.id.id_runnerName, summary.runnerName)
        }
    }

    /**
     * Utility class.
     * Check for Summaries whose race time is now in the past, and update accordingly.
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
     * Utility class. Setup the entry point accessors.
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
