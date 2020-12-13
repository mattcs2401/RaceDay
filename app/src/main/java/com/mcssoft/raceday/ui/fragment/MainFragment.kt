package com.mcssoft.raceday.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import com.mcssoft.raceday.R
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
            else -> super.onOptionsItemSelected(item)
        }
        return true
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
        // Add dividers between row items.
        val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding?.idRecyclerView?.addItemDecoration(decoration)

        // Set the recycler view.
        binding?.idRecyclerView?.adapter = raceAdapter
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