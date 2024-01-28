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

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface IEntryPoints {
        fun schedule(): IAlarm
        fun cancel(): IAlarm
    }

    private lateinit var schedule: IAlarm
    private lateinit var cancel: IAlarm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val entryPoints =
            EntryPointAccessors.fromApplication(this, IEntryPoints::class.java)
        schedule = entryPoints.schedule()
        cancel = entryPoints.cancel()

        schedule.scheduleAlarm()

        setContent {
            RaceDayBasicTheme {
                NavGraph()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel.cancelAlarm()
    }
}
