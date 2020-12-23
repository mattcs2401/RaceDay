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
import com.mcssoft.raceday.utility.Constants
import com.mcssoft.raceday.utility.Constants.RESULT_FAILURE
import com.mcssoft.raceday.utility.Constants.RESULT_SUCCESS
import com.mcssoft.raceday.utility.RaceDayUtilities
import com.mcssoft.raceday.utility.RaceDownloadManager
import com.mcssoft.raceday.utility.RaceDownloadReceiver
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

    /**
     * Kick it all off.
     *
     * For "defaultStart":
     * Delete anything in local file storage, then hand over to the RaceDownloadManager to get the
     * new file, and from there, hand over to the RaceDownloadReceiver to parse the file data and
     * write to database (all previous database entries are deleted).
     *
     * For "reStart":
     * Simply use the previously saved database entries (and recreate repository cache). Also is an
     * attempt to reduce background processing if the app is closed and reopened (same day).
     */
    private fun initialise() {
        val path = raceDayUtilities.getPrimaryStoragePath()

        if(path != "") {
            raceDayPreferences.setDefaultRaceCodes()

            if(raceDayPreferences.getUseFile()) {

                if(raceDayUtilities.fileExists(File(path)) && raceDayUtilities.isFileToday(File(path))) {

                    // Use the information previously derived from the file.
                    reStart()

                } else {
                    // Either the file doesn't exist, or the file exists, but is not today.
                    defaultStart(path)
                }
            } else {
                // The use file preference is not set.
                defaultStart(path)
            }
        } else {
            binding.idTvProgress.text = "Fatal error: No primary storage exists."
            /* TODO - Maybe some sort of dialog ?*/
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
                binding.idTvProgress.text = "Initialise cache."
                raceDayRepository.createOrRefreshCache()

                // Navigate to MainFragment.
                navigateToMain()
            }
            RESULT_FAILURE -> {
                binding.idPbSplash.visibility = View.GONE
                binding.idTvProgress.text = requireContext().resources.getText(R.string.splash_failure)
                Log.d("TAG", "SplashFragment: Result failure. Error: " + event.message)

                // Note: This will generally mean the file has downloaded Ok, but was unable to be
                // parsed into RaceMeetings.
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
        binding.idTvProgress.text = "Initialise cache."
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

        /** TBA - prefs not actually being used ATT. **/
        // Delete (any previous) file reference in Preferences.
        raceDayPreferences.setFileId(Constants.MINUS_ONE_L)  // set by download Receiver.
        raceDayPreferences.setFileDate("")                   // set by - TBA.

        // Set the "file date" in the preferences.
        val date = raceDayUtilities.getDateToday(RaceDayUtilities.DateFormat.SLASH)
        raceDayPreferences.setFileDate(date)
        /**--------------------**/

        // Get the network (path) url.
        val url = raceDayUtilities.createRaceDayUrl(requireContext())

        // Download, parse and write today's data (this is where it kicks off for new).
        raceDownloadManager.downloadPage(url, path, "RaceDay.xml")
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