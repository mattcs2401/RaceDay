package com.mcssoft.raceday.hilt

import android.app.AlarmManager
import android.content.Context
import com.mcssoft.raceday.utility.alarm.AlarmImpl
import com.mcssoft.raceday.utility.alarm.IAlarm
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AlarmModule {
    @Singleton
    @Provides
    fun provideAlarmManager(
        @ApplicationContext context: Context
    ): AlarmManager {
        return context.getSystemService(AlarmManager::class.java)
    }

    @Singleton
    @Provides
    fun provideAlarmScheduler(
        @ApplicationContext context: Context,
        alarmManger: AlarmManager,
        coroutineScope: CoroutineScope
    ): IAlarm {
        return AlarmImpl(
            context,
            alarmManger,
            coroutineScope
        )
    }
}
