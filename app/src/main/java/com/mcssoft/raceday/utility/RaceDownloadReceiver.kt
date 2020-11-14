package com.mcssoft.raceday.utility

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.mcssoft.raceday.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RaceDownloadReceiver : BroadcastReceiver() {

    @Inject lateinit var raceDownloadManager: RaceDownloadManager

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("TAG","RaceDownloadReceiver.onReceive()")
        when(intent.action) {
            DownloadManager.ACTION_DOWNLOAD_COMPLETE -> {
                Log.d("TAG","DownloadManager.ACTION_DOWNLOAD_COMPLETE")
                // Get the file id of the downloaded file.
                val fileId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,
                    Constants.MINUS_ONE)

                if (getDownloadStatus(fileId)) {//context, fileId)) {
//                    iFileData.setFileData(fileId, RaceDayUtil.getDateToday(RaceDayUtil.DateFormat.SLASH))

                    // TODO - can this be replaced with coroutine ?
//                    toBackgroundService(context, fileId)
                }
            }
        }
    }

    /**
     * Get the download status from the RaceDownloadManager.
     * @param fileId: The id of the downloaded file.
     * @return True if download was successful, else false.
     */
    private fun getDownloadStatus(fileId: Long): Boolean {
        if(fileId == Constants.MINUS_ONE) {
            return false
        }
        val query = DownloadManager.Query().setFilterById(fileId)
        val cursor = raceDownloadManager.getCursor(query)
        if(cursor.moveToFirst()) {
            when(cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                DownloadManager.STATUS_SUCCESSFUL -> {
                    Log.d("TAG","DownloadManager.STATUS_SUCCESSFUL")
                    return true
                }
                DownloadManager.STATUS_FAILED -> {
                    //https://developer.android.com/reference/android/app/DownloadManager
                    //When COLUMN_STATUS is STATUS_FAILED, this indicates the type of error that
                    // occurred. If an HTTP error occurred, this will hold the HTTP status code as
                    // defined in RFC 2616. Otherwise, it will hold one of the ERROR_* constants.
                    val error = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_REASON))

                    Log.d("TAG","DownloadManager.STATUS_FAILED")
                    // TODO - some sort of notification ? try again ?
                }
                DownloadManager.STATUS_PAUSED -> {
                    //https://developer.android.com/reference/android/app/DownloadManager
                    //When COLUMN_STATUS is STATUS_PAUSED, this indicates why the download is paused.
                    // It will hold one of the PAUSED_* constants.
                    Log.d("TAG","DownloadManager.STATUS_PAUSED")
                    // TODO - some sort of notification ? is continue automatic ?
                }
            }
        }
        return false
    }

    /**
     * Collate values and hand off to the background service.
     * @param fileId: Id of the downloaded file.
     * @param context: Only used by the DownloadService.
     */
    private fun toBackgroundService(context: Context, fileId: Long) {
        Log.d("TAG","RaceDownloadReceiver.toBackgroundService")
        // Bundle to hold values.
        val bundle = Bundle()
        bundle.putLong(context.getString(R.string.key_extras_file_id), fileId)

        // Intent with custom action.
        // Dev Notes: The action must be in a namespace because Intents are used globally.
        val intent = Intent(context.resources.getString(R.string.file_action))
        intent.putExtra(context.resources.getString(R.string.file_bundle), bundle)

//        DownloadService.enqueueWork(context.applicationContext as Application, intent)
    }

}

