package com.mcssoft.raceday.hilt_di

import android.content.Context
import com.mcssoft.raceday.utility.RaceDownloadManager
import com.mcssoft.raceday.utility.RaceDownloadReceiver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@InstallIn(ActivityComponent::class)
@Module
class DownloadModule {

    @Provides
    fun provideDownloadManager(@ActivityContext context: Context): RaceDownloadManager = RaceDownloadManager(context)

    @Provides
    fun provideDownloadReceiver(): RaceDownloadReceiver = RaceDownloadReceiver()

}