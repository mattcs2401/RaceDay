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
import com.mcssoft.raceday.utility.RaceDayFileUtilities
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
//    @Inject lateinit var raceDayRepository: RaceDayRepository
    @Inject lateinit var raceDayFileUtils: RaceDayFileUtilities

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
        val path = raceDayFileUtils.primaryStoragePath()
        if(path != "") {
            // Delete whatever file is there.
            raceDayFileUtils.deleteFromStorage(File(path))

            // Download, parse and write today's data (this is where it kicks off).
            raceDownloadManager.downloadPage(path, "RaceDay.xml")
        } else {
            /* TODO - primary storage path doesn't exist. */
        }
    }

    private lateinit var binding: SplashFragmentBinding
    private lateinit var downloadFilter: IntentFilter
}