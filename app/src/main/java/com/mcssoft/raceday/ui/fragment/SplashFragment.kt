package com.mcssoft.raceday.ui.fragment

import android.app.DownloadManager
import android.content.IntentFilter
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mcssoft.raceday.databinding.SplashFragmentBinding
import com.mcssoft.raceday.repository.RaceDayRepository
import com.mcssoft.raceday.utility.RaceDownloadManager
import com.mcssoft.raceday.utility.RaceDownloadReceiver
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

/**
 * Utility class that basically just does the initial network and file operations.
 */
@AndroidEntryPoint
class SplashFragment: Fragment() {

    @Inject lateinit var raceDownloadManager: RaceDownloadManager
    @Inject lateinit var raceDownloadReceiver: RaceDownloadReceiver
    @Inject lateinit var raceDayRepository: RaceDayRepository

    //<editor-fold default state="collapsed" desc="Region: Lifecycle">
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAG","SplashFragment.onCreate")

        downloadFilter = IntentFilter().apply {
            addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        Log.d("TAG","SplashFragment.onCreateView")

        return SplashFragmentBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("TAG","MainFragment.onViewCreated")

        binding = SplashFragmentBinding.bind(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("TAG","SplashFragment.onActivityCreated")

        requireContext().registerReceiver(raceDownloadReceiver, downloadFilter)
    }

    override fun onStart() {
        super.onStart()

        initialise()
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG","SplashFragment.onStop")

        requireContext().unregisterReceiver(raceDownloadReceiver)
    }
    //</editor-fold>

    @Suppress("ControlFlowWithEmptyBody")
    private fun initialise() {
        val path = primaryStoragePath()
        if(path != "") {
            // delete whatever is there.
            deleteFromStorage(File(path))
            // download today's data.
            raceDownloadManager.downloadPage(path, "RaceDay.xml")
        } else {
            /* TODO - primary storage path doesn't exist. */
        }
    }

    //<editor-fold default state="collapsed" desc="Region: Utility - File">
    /**
     * Delete all the files from the storage.
     * @param file: A File object representing the directory.
     */
    private fun deleteFromStorage(file: File) {
        if(filesExist(file)) {
            val listing = file.listFiles()
            for (file in listing) {
                file.delete()
            }
        }
    }

//    /**
//     * Check if the downloaded file has today's date (day and month).
//     * @param file: The downloaded file.
//     * @return True if the file day/month detail is the same as today.
//     */
//    private fun isFileToday(file: File): Boolean {
//        return RaceDayUtil.compareDateToToday(file.lastModified())
//    }

    /**
     * Check if there are any files in the download directory.
     * @param file: A file object pre-set with a path (the download directory).
     * @return True if files exist in the path.
     */
    private fun filesExist(file: File): Boolean {
        if (file.listFiles()!!.isNotEmpty()) {
            return true
        }
        return false
    }

    /**
     * Get the path from the primary storage.
     * @return The path value (end point represents a directory), else a blank string "".
     */
    private fun primaryStoragePath(): String {
        var path = ""
        if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            path = ContextCompat.getExternalFilesDirs(requireContext().applicationContext, null)[0].toString()
        }
        return path
    }
    //</editor-fold>

    private lateinit var binding: SplashFragmentBinding
    private lateinit var downloadFilter: IntentFilter
}