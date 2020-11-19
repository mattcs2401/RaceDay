package com.mcssoft.raceday.utility

import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.util.Log
import com.mcssoft.raceday.R
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.InputStream
import javax.inject.Inject

class RaceDownloadManager @Inject constructor(private val context: Context) {

    private var downloadManager: DownloadManager =
        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    /**
     * Basically a simple wrapper for the DownloadManager.enqueue()
     * @param path: The path to the directory in the storage.
     * @param fileName: The name of the file.
     */
    fun downloadPage(path: String, fileName: String) {
        Log.d("TAG","RaceDownloadManager.downloadPage")

        val url = RaceDayUtil.createRaceDayUrl(context)
        val file = File(path, fileName)

        val dlRequest = DownloadManager.Request(Uri.parse(url))
            .setTitle(context.resources.getString(R.string.raceday_downloading))
            .setDescription(context.resources.getString(R.string.downloading_details_for_raceday))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationUri(Uri.fromFile(file))

        downloadManager.enqueue(dlRequest)
    }

    /**
     * Returns a downloaded file (as InputStream) by the given file identifier [fileId].
     */
    fun getFile(fileId: Long): InputStream =
        ParcelFileDescriptor.AutoCloseInputStream(downloadManager.openDownloadedFile(fileId))

    /**
     * Returns the cursor associated with the RaceDownloadManager.Query [query].
     * @Note: Basically only used by the receiver to query the download status.
     */
    fun getCursor(query: DownloadManager.Query): Cursor = downloadManager.query(query)

    /**
     * Simple check that there is a network connection.
     */
    private fun isNetworkConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
/*
  Download file location:
  sdcard/Android/data/...
 */
