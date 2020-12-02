package com.mcssoft.raceday.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mcssoft.raceday.database.entity.RaceMeeting
import com.mcssoft.raceday.databinding.MainFragmentBinding
import com.mcssoft.raceday.interfaces.IMain
import com.mcssoft.raceday.ui.adapter.RaceMeetingAdapter
import com.mcssoft.raceday.ui.observer.RaceDayObserver
import com.mcssoft.raceday.utility.RaceDayBackPressCB
import com.mcssoft.raceday.viewmodel.RaceDayViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), IMain {

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
        // Set view model.
        mainViewModel.getCache().asLiveData()
                .observe(viewLifecycleOwner, RaceDayObserver(mainViewModel))
        // Adapter.
        raceAdapter = RaceMeetingAdapter(this)
        // Set the recycler view.
        recyclerView = binding?.idRecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        recyclerView!!.adapter = raceAdapter

        val bp = ""
    }

    //<editor-fold default state="collapsed" desc="Region: Interface-IMain">
    override fun getRaceMeetingCount(): Int {
        return mainViewModel.getCacheCount()
    }

    override fun getAt(ndx: Int): RaceMeeting {
        return mainViewModel.getAt(ndx)
    }

//    override fun insert(raceDetails: RaceMeeting): Long {
//        TODO("Not yet implemented")
//    }

//    override fun deleteAt(ndx: Int) {
//        TODO("Not yet implemented")
//    }

//    override fun notifyItemRemoved(position: Int) {
//        TODO("Not yet implemented")
//    }

//    override fun onTypeSelect(selType: Int, position: Int) {
//        TODO("Not yet implemented")
//    }

//    override fun getMeetingCode(ndx: Int): String {
//        TODO("Not yet implemented")
//    }
    //</editor-fold>

    // UI components.
    private var binding : MainFragmentBinding? = null
    // Main view model.
    private val mainViewModel: RaceDayViewModel by viewModels()

    // Callback to block the user from pressing back (otherwise will reload the SplashFragment).
    private lateinit var raceDayBackPressCallback : RaceDayBackPressCB

    private var raceAdapter: RaceMeetingAdapter? = null
    private var recyclerView: RecyclerView? = null
}