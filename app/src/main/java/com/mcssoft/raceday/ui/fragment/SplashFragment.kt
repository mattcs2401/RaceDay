package com.mcssoft.raceday.ui.fragment

import android.app.DownloadManager
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.mcssoft.raceday.R
import com.mcssoft.raceday.databinding.SplashFragmentBinding
import com.mcssoft.raceday.events.MessageEvent
import com.mcssoft.raceday.utility.Constants.RESULT_FAILURE
import com.mcssoft.raceday.utility.Constants.RESULT_SUCCESS
import com.mcssoft.raceday.utility.RaceDayFileUtilities
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

    @Inject lateinit var raceDownloadManager: RaceDownloadManager
    @Inject lateinit var raceDownloadReceiver: RaceDownloadReceiver
//    @Inject lateinit var raceDayRepository: RaceDayRepository
    @Inject lateinit var raceDayFileUtils: RaceDayFileUtilities

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
        EventBus.getDefault().register(this)
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when(event.result) {
            RESULT_SUCCESS -> {

                // Notes: This is the end point of the file download and parse. The RaceDay.xml file
                // has been downloaded by the DownLoadManager and saved to local storage. A broadcast
                // has sent and picked up by the DownloadReceiver. The receiver has spawned a Worker
                // to parse and write the file data in the background.

                // TODO - notify and stop spinner in SplashFragment, navigate to MainFragment.
                Toast.makeText(requireContext(), "SplashFragment: Result success", Toast.LENGTH_SHORT).show()

                binding.progressBar.visibility = View.GONE

                try {
                    val controller = Navigation.findNavController(requireActivity(), R.id.id_nav_host_fragment)
                    //requireActivity().findNavController(R.id.id_nav_host_fragment)
                    controller.navigate(R.id.action_id_splash_fragment_to_id_main_fragment)//R.id.id_main_fragment)
                } catch(ex: Exception) {
                    val msg = ex.message
                    val bp = "bp"
                }
            }
            RESULT_FAILURE -> {
                Toast.makeText(requireContext(), "SplashFragment: Result failure", Toast.LENGTH_SHORT).show()
            }
        }

        /* Do something */
    }

    private lateinit var binding: SplashFragmentBinding
    private lateinit var downloadFilter: IntentFilter
}