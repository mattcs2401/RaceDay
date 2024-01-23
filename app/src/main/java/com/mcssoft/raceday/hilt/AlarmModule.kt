package com.mcssoft.raceday.hilt

import android.app.AlarmManager
import android.content.Context
import androidx.datastore.core.DataStore
import com.mcssoft.raceday.data.repository.preferences.user.UserPreferences
import com.mcssoft.raceday.utility.alarm.AlarmImpl
import com.mcssoft.raceday.utility.alarm.IAlarm
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
        userPrefs: DataStore<UserPreferences>
    ): IAlarm {
        return AlarmImpl(
            context,
            alarmManger,
            userPrefs
        )
    }
}
