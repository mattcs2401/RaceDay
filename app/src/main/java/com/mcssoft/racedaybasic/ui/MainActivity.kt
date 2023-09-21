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

    override fun onStart() {
        super.onStart()
//        registerReceiver(receiver, null)    // filer TBA.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Intent(applicationContext, NotifyService::class.java).also {intent ->
            intent.action = NotifyService.Actions.START.toString()
            startService(intent)
        }

        setContent {
            RaceDayBasicTheme {
                NavGraph()
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
    }

}
