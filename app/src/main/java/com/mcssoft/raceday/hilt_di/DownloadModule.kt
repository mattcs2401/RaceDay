package com.mcssoft.raceday.hilt_di

import android.content.Context
import com.mcssoft.raceday.utility.RaceDownloadManager
import com.mcssoft.raceday.utility.RaceDownloadReceiver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DownloadModule {

    @Singleton
    @Provides
    fun provideDownloadManager(@ApplicationContext context: Context): RaceDownloadManager {
        return RaceDownloadManager(context)
    }

    @Provides
    fun provideDownloadReceiver(): RaceDownloadReceiver {
        return RaceDownloadReceiver()
    }

}