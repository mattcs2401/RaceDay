package com.mcssoft.raceday.utility

import android.content.Context
import android.os.Environment
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import java.io.File
import javax.inject.Inject

class RaceDayFileUtilities @Inject constructor(private val context: Context) {

    /**
     * Delete all the files from the storage.
     * @param file: A File object representing the directory.
     */
    fun deleteFromStorage(file: File) {
        if(filesExist(file)) {
            val listing = file.listFiles()
            listing?.forEach { f ->
                f.delete()
            }
        }
    }

    /**
     * Check if the downloaded file has today's date (day and month).
     * @param file: The downloaded file.
     * @return True if the file day/month detail is the same as today.
     */
    private fun isFileToday(file: File): Boolean {
        return RaceDayUtilities.compareDateToToday(file.lastModified())
    }

    /**
     * Check if there are any files in the download directory.
     * @param file: A file object pre-set with a path (the download directory).
     * @return True if files exist in the path.
     */
    fun filesExist(file: File): Boolean {
        if (file.listFiles()!!.isNotEmpty()) {
            return true
        }
        return false
    }

    /**
     * Get the path from the primary storage.
     * @return The path value (end point represents a directory), else a blank string "".
     */
    fun primaryStoragePath(): String {
        var path = ""
        if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            path = ContextCompat.getExternalFilesDirs(context.applicationContext, null)[0].toString()
        }
        return path
    }

}