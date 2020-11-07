package com.mcssoft.raceday.ui.fragment

import android.app.DownloadManager
import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.mcssoft.raceday.databinding.MainFragmentBinding
import com.mcssoft.raceday.utility.RaceDownloadManager
import com.mcssoft.raceday.utility.RaceDownloadReceiver
import com.mcssoft.raceday.viewmodel.FileMetaDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject lateinit var viewModel: FileMetaDataViewModel
    @Inject lateinit var raceDownloadManager: RaceDownloadManager
    @Inject lateinit var raceDownloadReceiver: RaceDownloadReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAG","MainFragment.onCreate")

        downloadFilter = IntentFilter().apply {
            addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        Log.d("TAG","MainFragment.onCreateView")

        return MainFragmentBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("TAG","MainFragment.onViewCreated")

        binding = MainFragmentBinding.bind(view)

        viewModel.getAllFile().observe(viewLifecycleOwner, Observer { file ->
            // TBA
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("TAG","MainFragment.onActivityCreated")

        requireContext().registerReceiver(raceDownloadReceiver, downloadFilter)

    }

    override fun onStart() {
        super.onStart()

        val path = ContextCompat.getExternalFilesDirs(requireContext().applicationContext, null)[0].toString()
        val path2 = requireContext().filesDir
        val file = File(path2, "RaceDay.xml")

        raceDownloadManager.downloadMainPage()

//        val filePath = Environment.getExternalStorageDirectory().toString() //+ File.separator
//        val dataDir = Environment.getDataDirectory().toString()
//        val cacheDir = Environment.getDownloadCacheDirectory().toString()
//        val storageState = Environment.getExternalStorageState()
//
//        val value = requireContext().filesDir.toString()
//        val list = requireContext().fileList()
//        val val2 = requireContext().cacheDir.toString()
//        val val3 = requireContext().externalCacheDir.toString()

        val externalStorageVolumes: Array<out File> =
                ContextCompat.getExternalFilesDirs(requireContext().applicationContext, null)
        val primaryExternalStorage = externalStorageVolumes[0].toString()
        val secondExternalStorage = externalStorageVolumes[1].toString()

        val bp = "bp"
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG","MainFragment.onStop")

        requireContext().unregisterReceiver(raceDownloadReceiver)
    }

    private lateinit var binding: MainFragmentBinding
    private lateinit var downloadFilter: IntentFilter
    private lateinit var viewStub: ViewStub
}