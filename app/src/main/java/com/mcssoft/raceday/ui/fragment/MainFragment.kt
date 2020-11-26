package com.mcssoft.raceday.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mcssoft.raceday.databinding.MainFragmentBinding
import com.mcssoft.raceday.utility.RaceDayBackPressCB

//@AndroidEntryPoint
class MainFragment : Fragment() {

    //<editor-fold default state="collapsed" desc="Region: Lifecycle">
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        Log.d("TAG","MainFragment.onCreateView")

        return MainFragmentBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("TAG","MainFragment.onViewCreated")

        binding = MainFragmentBinding.bind(view)

        // Setup the UI and related components.
        initialise()

//        viewModel.getAllFile().observe(viewLifecycleOwner, Observer { file ->
//            // TBA
//        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("TAG","MainFragment.onActivityCreated")

        // Init back press handler callback.
        raceDayBackPressCallback = RaceDayBackPressCB(true )
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG","MainFragment.onStart")

        // Add on back pressed handler.
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, raceDayBackPressCallback)
    }

    override fun onStop() {
        Log.d("TAG","MainFragment.onStop")

        // Remove back press handler callback.
        raceDayBackPressCallback.removeCallback()

        // Super.
        super.onStop()
    }
    //</editor-fold>

    private fun initialise() {
//        var cache = listOf<FileMetaData>()
//        if (viewModel.getCountFileMeta() > 0) {
//            cache = viewModel.getAllFile() as List<FileMetaData>
//        }

        val bp = ""
    }

    private lateinit var binding : MainFragmentBinding

    // Callback to block the user from pressing back (otherwise will reload the SplashFragment).
    private lateinit var raceDayBackPressCallback : RaceDayBackPressCB
}