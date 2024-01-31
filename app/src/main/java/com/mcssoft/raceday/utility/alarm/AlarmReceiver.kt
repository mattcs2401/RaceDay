package com.mcssoft.raceday.utility.alarm

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mcssoft.raceday.R
import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.domain.model.Summary
import com.mcssoft.raceday.utility.Constants.FIVE
import com.mcssoft.raceday.utility.Constants.INTENT_ACTION
import com.mcssoft.raceday.utility.Constants.SIXTY
import com.mcssoft.raceday.utility.Constants.THOUSAND
import com.mcssoft.raceday.utility.Constants.TWENTY_FIVE
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

class AlarmReceiver : BroadcastReceiver() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface IEntryPoints {
        fun dbAccess(): IDbRepo
        fun notificationManager(): INotification
        fun notificationBuilder(): INotification
    }

    private lateinit var dataAccess: IDbRepo
    private lateinit var notificationManager: NotificationManagerCompat
    private lateinit var notificationBuilder: NotificationCompat.Builder

    override fun onReceive(context: Context, intent: Intent?) {
        when (intent?.action) {
            INTENT_ACTION -> {
                Toast.makeText(context, "TBA - Notification clicked.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        // Setup the notification and database accessors.
        setEntryPoints(context)

        goAsync {
            processReceive(context)
        }
    }

    private suspend fun processReceive(context: Context) {
        Log.d("TAG", "AlarmReceiver.processReceive()")
        // The current time in millis.
        val currentTime = DateUtils().getCurrentTimeMillis()
        // List of Summaries that are notification candidates.
        val notifyList = mutableListOf<Summary>()
        // Check for any Summaries where the race time is now in the past and update accordingly.
        checkSummaries(currentTime)
        // A time "window" of the current time -> (current time + five minutes).
        val windowTime = currentTime + (THOUSAND * SIXTY * FIVE).toLong()
        // Get a list of Summaries whose race time is still in the future.
        delay(TWENTY_FIVE) // TBA ?
        val baseNotifyList = dataAccess.getCurrentSummaries()
        delay(TWENTY_FIVE) // TBA
        // Create a list of Summaries whose race time is within the time window.
        if (baseNotifyList.isEmpty()) {
            return
        } else {
            for (summary in baseNotifyList) {
                if (DateUtils().compareToWindowTime(summary.raceStartTime, windowTime)) {
                    notifyList.add(summary)
                }
            }
        }
        // Send out notifications and update the Summary as having been notified.
        if (notifyList.isEmpty()) {
            return
        } else {
            for (summary in notifyList) {
                summary.isNotified = true
                dataAccess.updateSummary(summary)
                delay(TWENTY_FIVE) // TBA
                sendNotification(context, summary)
            }
        }
    }

    @SuppressLint("MissingPermission") // Permission is in the manifest, but still complains.
    private fun sendNotification(context: Context, summary: Summary) {
        val bundle = Bundle().also { bndl ->
            bndl.putLong("key_summary_id", summary._id)
        }
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = INTENT_ACTION
        }
        val pIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val view = buildView(context, summary, pIntent)
        notificationBuilder.also { ncb ->
            ncb.setExtras(bundle)
            ncb.setCustomContentView(view)
        }
        notificationManager.notify(
            System.currentTimeMillis().toInt(),
            notificationBuilder.build()
        )
    }

    /**
     * Build the Notification's view from Summary details.
     * @param context: For system string resources.
     * @param summary: The Summary to draw data from.
     * @param pendingIntent: The intent associated with the notification.
     */
    private fun buildView(context: Context, summary: Summary, pendingIntent: PendingIntent): RemoteViews {
        return RemoteViews(context.packageName, R.layout.layout_notification).also { rvs ->
            rvs.setTextViewText(R.id.id_sellCode, summary.sellCode)
            rvs.setTextViewText(R.id.id_venueMnemonic, summary.venueMnemonic)
            rvs.setTextViewText(R.id.id_raceNumber, summary.raceNumber.toString())
            rvs.setTextViewText(R.id.id_runnerNumber, "(H${summary.runnerNumber})")
            rvs.setTextViewText(R.id.id_raceTime, summary.raceStartTime)
            rvs.setTextViewText(R.id.id_runnerName, summary.runnerName)
            rvs.setOnClickPendingIntent(R.id.id_action_button, pendingIntent)
        }
    }

    /**
     * Utility class.
     * Check for Summaries whose race time is now in the past, and update accordingly. We want to
     * discount these from any Summary notifications.
     * @param currentTime: The current time in millis.
     */
    private suspend fun checkSummaries(currentTime: Long) {
        var raceTime: Long

        val lSummaries = dataAccess.getSummaries()

        for (summary in lSummaries) {
            raceTime = DateUtils().getCurrentTimeMillis(summary.raceStartTime)

            if (!summary.isPastRaceTime) {
                if (currentTime > raceTime) {
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
        // Notification.
        notificationManager = entryPoints.notificationManager().getNotificationManager()
        notificationBuilder = entryPoints.notificationBuilder().getNotificationBuilder()
        // Database access.
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
        } catch (ex: Exception) {
            Log.e("TAG", "Exception in BroadcastReceiver.goAsync: ${ex.message}")
            throw(ex)
        } finally {
            pendingResult.finish()
        }
    }
}
