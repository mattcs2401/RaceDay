package com.mcssoft.raceday.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mcssoft.raceday.ui.components.navigation.NavGraph
import com.mcssoft.raceday.ui.theme.RaceDayBasicTheme
import com.mcssoft.raceday.utility.services.NotifyService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Intent(applicationContext, NotifyService::class.java).also { intent ->
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
    }

}
