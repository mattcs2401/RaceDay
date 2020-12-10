package com.mcssoft.raceday.ui.fragment

import android.app.DownloadManager
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.mcssoft.raceday.R
import com.mcssoft.raceday.databinding.SplashFragmentBinding
import com.mcssoft.raceday.events.ResultMessageEvent
import com.mcssoft.raceday.repository.RaceDayPreferences
import com.mcssoft.raceday.repository.RaceDayRepository
import com.mcssoft.raceday.utility.*
import com.mcssoft.raceday.utility.Constants.RESULT_FAILURE
import com.mcssoft.raceday.utility.Constants.RESULT_SUCCESS
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File
import javax.inject.Inject


/**
 * Utility class that basically just does the initial network and file operations.
 */
@AndroidEntryPoint
class SplashFragment : Fragment() {

    @Inject lateinit var raceDayPreferences: RaceDayPreferences
    @Inject lateinit var raceDownloadManager: RaceDownloadManager
    @Inject lateinit var raceDownloadReceiver: RaceDownloadReceiver
    @Inject lateinit var raceDayUtilities: RaceDayUtilities
    @Inject lateinit var raceDayRepository: RaceDayRepository

    //<editor-fold default state="collapsed" desc="Region: Lifecycle">
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAG", "SplashFragment.onCreate")

        downloadFilter = IntentFilter().apply {
            addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        Log.d("TAG", "SplashFragment.onCreateView")
        return SplashFragmentBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("TAG", "MainFragment.onViewCreated")
        binding = SplashFragmentBinding.bind(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("TAG", "SplashFragment.onActivityCreated")

        requireContext().registerReceiver(raceDownloadReceiver, downloadFilter)
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG", "SplashFragment.onStart")

        EventBus.getDefault().register(this)
        // Kick it all off.
        initialise()
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG", "SplashFragment.onStop")

        EventBus.getDefault().unregister(this)
        requireContext().unregisterReceiver(raceDownloadReceiver)
    }
    //</editor-fold>

    @Suppress("ControlFlowWithEmptyBody")
    /**
     * Kick it all off.
     * Delete anything in local file storage, then hand over to the RaceDownloadManager to get the
     * new file, and from there, hand over to the RaceDownloadReceiver to parse the file data
     * and write to database (all previous database entries are deleted).
     */
    private fun initialise() {
        val path = raceDayUtilities.primaryStoragePath()

        if(path != "") {
            if(!raceDayPreferences.getFileUse()) {
                /* Basically delete everything and recreate. This will be the default when the app
                   first runs.
                */
                defaultStart(path)
            } else {
                if(raceDayUtilities.fileExists(File(path))) {

                    if(raceDayUtilities.isFileToday(File(path))) {
                        // Use the file.
                        reStart()
                    } else {
                        // A file exists but is not today.
                        defaultStart(path)
                    }
                } else {
                    // No file exists.
                    defaultStart(path)
                }
            }
        } else {
            /* TODO - Primary storage path doesn't exist. Maybe some sort of dialog ?*/
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ResultMessageEvent) {
        when(event.result) {
            RESULT_SUCCESS -> {

            /* Notes:
               This is the end point of the file download and parse. The RaceDay.xml file has been
               downloaded by the DownLoadManager and saved to local storage. A broadcast has also
               been sent by the DownloadManager and picked up by the DownloadReceiver. The receiver
               has spawned a (Coroutine) Worker to parse and write the file data in the background.
            */
               Log.d("TAG", "SplashFragment: Result success")

                // Create repository cache.
                raceDayRepository.createOrRefreshCache()

                // Navigate to MainFragment.
                navigateToMain()
            }
            RESULT_FAILURE -> {
                binding.progressBar.visibility = View.GONE
                binding.textView.text = "SplashFragment: Processing result failure"
                Log.d("TAG", "SplashFragment: Result failure")

                // TODO - some sort of retry mechanism ?
            }
        }
    }

    /**
     * Start again utilising the previously saved data.
     */
    private fun reStart() {
        Log.d("TAG", "SplashFragment: Restart")

        // Create repository cache.
        raceDayRepository.createOrRefreshCache()

        // Navigate to MainFragment.
        navigateToMain()
    }

    /**
     * The default starting point for file download (and then onto processing via the download
     * receiver).
     */
    private fun defaultStart(path: String) {
        Log.d("TAG", "SplashFragment: Default start")
        // Delete whatever file is there.
        raceDayUtilities.deleteFromStorage(File(path))

        // Clear cache and underlying data. Is recreated on successful download processing.
        raceDayRepository.clearCache()

        // Delete (any previous) file reference in Preferences.
        raceDayPreferences.setFileId(Constants.MINUS_ONE_L)  // set by download Receiver.
        raceDayPreferences.setFileDate("")                   // set by - TBA.

        // Set the "file date" in the preferences.
        val date = raceDayUtilities.getDateToday(RaceDayUtilities.DateFormat.SLASH)
        raceDayPreferences.setFileDate(date)

        // Get the network (path) url.
        val url = raceDayUtilities.createRaceDayUrl(requireContext())

        // Download, parse and write today's data (this is where it kicks off for new).
        raceDownloadManager.downloadPage(url, path, "RaceDay.xml")

//        /** Testing **/
//        EventBus.getDefault().post(ResultMessageEvent(Constants.RESULT_SUCCESS))
    }

    private fun navigateToMain() {
        // Navigate to MainFragment.
        Navigation.findNavController(requireActivity(), R.id.id_nav_host_fragment)
                .navigate(R.id.action_splashFragment_to_mainFragment)
    }

    private lateinit var binding: SplashFragmentBinding
    private lateinit var downloadFilter: IntentFilter

    private var fileUse: Boolean? = null// = false
}