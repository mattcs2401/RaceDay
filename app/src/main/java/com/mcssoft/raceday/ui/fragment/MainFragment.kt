package com.mcssoft.raceday.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mcssoft.raceday.ui.adapter.RaceMeetingAdapter
import com.mcssoft.raceday.utility.RaceDayBackPressCB
import com.mcssoft.raceday.viewmodel.RaceDayViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.mcssoft.raceday.databinding.MainFragmentBinding

@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject lateinit var raceAdapter: RaceMeetingAdapter
    @Inject lateinit var mainViewModel: RaceDayViewModel

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

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
    //</editor-fold>

    private fun initialise() {
        // Set the recycler view.
        binding?.idRecyclerView?.adapter = raceAdapter

        // Set view model.
        mainViewModel.meetings?.observe(viewLifecycleOwner) { mtgs ->
            raceAdapter.submitList(mtgs)
        }
//        val bp = "bp"
    }

    // UI components.
    private var binding : MainFragmentBinding? = null

    // Callback to block the user from pressing back (otherwise will reload the SplashFragment).
    private lateinit var raceDayBackPressCallback : RaceDayBackPressCB
}