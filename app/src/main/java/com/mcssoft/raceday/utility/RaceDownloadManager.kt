package com.mcssoft.raceday.utility

import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.util.Log
import java.io.File
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RaceDownloadManager @Inject constructor(private val context: Context) {

    private var downloadManager: DownloadManager =
        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    fun downloadMainPage() {
        Log.d("TAG","RaceDownloadManager.downloadMainPage")
        val url = RaceDayUtil.createRaceDayUrl(context)
        val file = File(context.getExternalFilesDir(null), "RaceDay.xml")

        val dlRequest = DownloadManager.Request(Uri.parse(url))
            .setTitle("RaceDay Downloading")
            .setDescription("Downloading details for RaceDay")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationUri(Uri.fromFile(file))

        downloadManager.enqueue(dlRequest)
    }

    fun downloadPage(page: String) {
        // TBA
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

}
/*
  Download file location:
  sdcard/Android/data/...
 */

/*
  private fun isNetworkConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
    return networkCapabilities != null &&
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
  }
 */