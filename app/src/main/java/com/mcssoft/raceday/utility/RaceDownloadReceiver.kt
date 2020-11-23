package com.mcssoft.raceday.utility

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.mcssoft.raceday.R
import com.mcssoft.raceday.repository.RaceDayPreferences
import com.mcssoft.raceday.worker.RaceDayParseWorker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RaceDownloadReceiver : BroadcastReceiver() {

    @Inject lateinit var raceDayPreferences: RaceDayPreferences

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("TAG","RaceDownloadReceiver.onReceive()")
        when(intent.action) {
            DownloadManager.ACTION_DOWNLOAD_COMPLETE -> {
                Log.d("TAG","DownloadManager.ACTION_DOWNLOAD_COMPLETE")

                // Get the file id of the downloaded file.
                val fileId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,
                    Constants.MINUS_ONE_L)

                if(fileId != Constants.MINUS_ONE_L) {
                    // Download was successful (ATT testing ?).
                    Toast.makeText(context, "Download successful. File id=$fileId", Toast.LENGTH_SHORT).show()

                    // Save the file id to preferences.
                    raceDayPreferences.setFileId(fileId)

                    // Parse the file data.
                    parseFileData(context, fileId)

                } else {
                    // TODO - some sort of retry strategy based on the DownloadManager COLUMN_REASON
                    //  and COLUMN_STATUS codes.

                    // Download was not successful.
                    Toast.makeText(context, "Download not successful.", Toast.LENGTH_SHORT).show()

                    raceDayPreferences.setFileId(Constants.MINUS_ONE_L)
                }
            }
        }
    }

    /**
     * Hand off the file processing to a background (WorkManager) operation.
     * @param context: Mainly just used for Resources and WorkManager instance.
     * @param fileId: Id of the downloaded file.
     */
    private fun parseFileData(context: Context, fileId: Long) {
        val workData = workDataOf(context.resources.getString(R.string.key_file_id) to fileId)
        val raceDayParseWorker = OneTimeWorkRequestBuilder<RaceDayParseWorker>()
                .setInputData(workData).
                build()
        WorkManager.getInstance(context).enqueue(raceDayParseWorker)

    }

}

