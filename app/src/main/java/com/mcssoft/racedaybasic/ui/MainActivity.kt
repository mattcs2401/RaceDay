package com.mcssoft.racedaybasic.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mcssoft.racedaybasic.ui.components.navigation.NavGraph
import com.mcssoft.racedaybasic.ui.theme.RaceDayBasicTheme
import com.mcssoft.racedaybasic.utility.services.NotifyService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    @Inject lateinit var networkObserver: NetworkObserver

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
//                val status by networkObserver.observe()
//                    .collectAsState(INetworkObserver.Status.Unavailable)

                NavGraph()//status.toString())//applicationContext)
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

//@InstallIn(ActivityComponent::class)
//@Module
//class NetworkModule {
//    @Singleton
//    @Provides
//    fun providesNetwork(): INetworkObserver {
//        return NetworkObserver()
//    }
//}
