package com.mcssoft.racedaybasic.ui

import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mcssoft.racedaybasic.ui.components.navigation.NavGraph
import com.mcssoft.racedaybasic.ui.theme.RaceDayBasicTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    @Inject
//    lateinit var receiver: BroadcastReceiver
//    @Inject
//    lateinit var connectivityManager: ConnectivityManager

    override fun onStart() {
        super.onStart()

//        registerReceiver(receiver, null)    // filer TBA.
//        connectivityManager.registerConnectionObserver(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // TODO - integrate network availability.
//            val isNetworkAvailable = connectivityManager.isNetworkAvailable.value

            RaceDayBasicTheme {
                NavGraph()//applicationContext)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

//        unregisterReceiver(receiver)
//        connectivityManager.unregisterConnectionObserver(this)
    }

}