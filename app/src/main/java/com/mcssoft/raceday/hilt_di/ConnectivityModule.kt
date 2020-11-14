package com.mcssoft.raceday.hilt_di

import android.content.Context
import com.mcssoft.raceday.utility.RaceConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
class ConnectivityModule {

    @Provides
    fun provideConnectivity(@ActivityContext context: Context): RaceConnectivityManager = RaceConnectivityManager(context)
}