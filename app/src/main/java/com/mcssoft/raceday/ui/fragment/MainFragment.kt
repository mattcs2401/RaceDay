package com.mcssoft.raceday.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mcssoft.raceday.R
import com.mcssoft.raceday.ui.adapter.RaceMeetingAdapter
import com.mcssoft.raceday.utility.RaceDayBackPressCB
import com.mcssoft.raceday.viewmodel.RaceDayViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.mcssoft.raceday.databinding.MainFragmentBinding
import com.mcssoft.raceday.interfaces.IDeleteAll
import com.mcssoft.raceday.ui.dialog.DeleteAllDialog
import com.mcssoft.raceday.utility.RaceDayUtilities
import java.io.File

@AndroidEntryPoint
class MainFragment : Fragment(), IDeleteAll {

    @Inject lateinit var mainViewModel: RaceDayViewModel
    @Inject lateinit var raceAdapter: RaceMeetingAdapter
    @Inject lateinit var raceDayUtilities: RaceDayUtilities

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
        Log.d("TAG","MainFragment.onDestroyView")
        binding = null
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        Log.d("TAG","MainFragment.onCreateOptionsMenu")

        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.id_menu_item_settings -> {
                // Navigate to MainFragment.
                Navigation.findNavController(requireActivity(), R.id.id_nav_host_fragment)
                    .navigate(R.id.action_mainFragment_to_preferencesFragment)
            }
            R.id.id_menu_item_delete_all -> {
                val dialog = DeleteAllDialog(this)
                dialog.show(parentFragmentManager, "")
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: Interface-IDeleteAll">
    override fun deleteAll(deleteAll: Boolean) {
        when(deleteAll) {
            true -> {
                raceDayUtilities.deleteFromStorage(File(raceDayUtilities.getPrimaryStoragePath()))
                mainViewModel.clearCache()
                Navigation.findNavController(requireActivity(), R.id.id_nav_host_fragment)
                        .navigate(R.id.action_mainFragment_to_splashFragment)
            }
        }
    }
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: Utility">
    private fun initialise() {
        // Fragment title in action/tool bar.
        setTitle()

        setHasOptionsMenu(true)

        setRecyclerView()

        setViewModel()
    }

    private fun setViewModel() {
        mainViewModel.meetings?.observe(viewLifecycleOwner) { mtgs ->
            raceAdapter.submitList(mtgs)
        }
    }

    private fun setRecyclerView() {
//        // There will only be the number of meetings as parsed from the download file.
//        binding?.idRecyclerView?.setHasFixedSize(true)

        // Add dividers between row items.
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding?.idRecyclerView?.addItemDecoration(decoration)

        // Set the recycler view.
        binding?.idRecyclerView?.adapter = raceAdapter

        // Hide the progress bar when the layout is complete.
        binding?.idRecyclerView?.layoutManager = object : LinearLayoutManager(requireActivity(), VERTICAL, false) {
            override fun onLayoutCompleted(state: RecyclerView.State?) {
                super.onLayoutCompleted(state)
                binding?.progressBar2?.visibility = View.GONE
            }
        }
    }

    private fun setTitle() {
        requireActivity()
            .findViewById<Toolbar>(R.id.id_toolbar)
            ?.title = "Race Day"
    }
    //</editor-fold>

    // UI components.
    private var binding : MainFragmentBinding? = null

    // Callback to block the user from pressing back (otherwise will reload the SplashFragment).
    private lateinit var raceDayBackPressCallback : RaceDayBackPressCB
}