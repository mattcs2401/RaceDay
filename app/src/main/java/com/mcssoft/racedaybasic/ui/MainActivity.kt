package com.mcssoft.racedaybasic.ui

import android.content.BroadcastReceiver
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mcssoft.racedaybasic.ui.components.navigation.NavGraph
import com.mcssoft.racedaybasic.ui.theme.RaceDayBasicTheme
import com.mcssoft.racedaybasic.utility.INetworkManager
import com.mcssoft.racedaybasic.utility.NetworkManager
import com.mcssoft.racedaybasic.utility.services.NotifyService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    @Module
//    @InstallIn(ActivityComponent::class)
//    abstract class NetworkModule {
//        @Binds
//        abstract fun bindsNetworkManager(
//            networkManager: NetworkManager
//        ): INetworkManager
//    }
//    @Inject
//    lateinit var networkManager: NetworkManager

    override fun onStart() {
        super.onStart()

//        registerReceiver(receiver, null)    // filer TBA.
//        connectivityManager.registerConnectionObserver(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Intent(applicationContext, NotifyService::class.java).also {intent ->
            intent.action = NotifyService.Actions.START.toString()
            startService(intent)
        }

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

        Intent(applicationContext, NotifyService::class.java).also {intent ->
            intent.action = NotifyService.Actions.STOP.toString()
            startService(intent)
        }
//        unregisterReceiver(receiver)
//        connectivityManager.unregisterConnectionObserver(this)
    }

}