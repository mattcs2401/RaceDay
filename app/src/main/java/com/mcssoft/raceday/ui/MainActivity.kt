package com.mcssoft.raceday.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mcssoft.raceday.ui.components.navigation.NavGraph
import com.mcssoft.raceday.ui.theme.RaceDayBasicTheme
import com.mcssoft.raceday.utility.alarm.IAlarm
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
/*
  Note:
  Using onCreate() and onDestroy() to schedule and cancel the Alarm respectively. This is because we
  need to keep the Alarm active as long as the app is running, even if the app is in the background.
 */
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface IEntryPoints {
        fun alarm(): IAlarm
    }

    private lateinit var iAlarm: IAlarm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        iAlarm = EntryPointAccessors.fromApplication(this, IEntryPoints::class.java).alarm()

        iAlarm.scheduleAlarm()

        setContent {
            RaceDayBasicTheme {
                NavGraph()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        iAlarm.cancelAlarm()
    }
}
