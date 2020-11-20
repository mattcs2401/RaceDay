package com.mcssoft.raceday.utility

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.mcssoft.raceday.repository.RaceDayPreferences
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
                    // Download was successful.
                    Toast.makeText(context, "Download successful. File id=$fileId", Toast.LENGTH_SHORT).show()

                     raceDayPreferences.setFileId(fileId)

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

//    /**
//     * Collate values and hand off to the background service.
//     * @param fileId: Id of the downloaded file.
//     * @param context: Only used by the DownloadService.
//     */
//    private fun toBackgroundService(context: Context, fileId: Long) {
//        Log.d("TAG","RaceDownloadReceiver.toBackgroundService")
//        // Bundle to hold values.
//        val bundle = Bundle()
//        bundle.putLong(context.getString(R.string.key_extras_file_id), fileId)
//
//        // Intent with custom action.
//        // Dev Notes: The action must be in a namespace because Intents are used globally.
//        val intent = Intent(context.resources.getString(R.string.file_action))
//        intent.putExtra(context.resources.getString(R.string.file_bundle), bundle)
//
//        DownloadService.enqueueWork(context.applicationContext as Application, intent)
//    }

}

