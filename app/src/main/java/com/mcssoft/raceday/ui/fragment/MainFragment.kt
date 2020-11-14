package com.mcssoft.raceday.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.mcssoft.raceday.database.entity.FileMetaData
import com.mcssoft.raceday.databinding.MainFragmentBinding
import com.mcssoft.raceday.interfaces.IDownloadReceiver
import com.mcssoft.raceday.viewmodel.FileMetaDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(), IDownloadReceiver {

    @Inject lateinit var viewModel: FileMetaDataViewModel

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        Log.d("TAG","MainFragment.onCreate")
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        Log.d("TAG","MainFragment.onCreateView")
        return MainFragmentBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("TAG","MainFragment.onViewCreated")

        binding = MainFragmentBinding.bind(view)

        initialise()

        viewModel.getAllFile().observe(viewLifecycleOwner, Observer { file ->
            // TBA
        })
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        Log.d("TAG","MainFragment.onActivityCreated")
//    }

//    override fun onStart() {
//        super.onStart()
//    }

//    override fun onStop() {
//        super.onStop()
//        Log.d("TAG","MainFragment.onStop")
//    }

    private fun initialise() {
        var cache = listOf<FileMetaData>()
        if (viewModel.getCountFileMeta() > 0) {
            cache = viewModel.getAllFile() as List<FileMetaData>
        }

        val bp = ""
    }

    private lateinit var binding: MainFragmentBinding
}