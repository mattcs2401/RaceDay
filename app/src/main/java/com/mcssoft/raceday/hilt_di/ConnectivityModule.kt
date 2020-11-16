package com.mcssoft.raceday.hilt_di

import android.content.Context
import com.mcssoft.raceday.utility.RaceConnectivityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object ConnectivityModule {

    @Singleton
    @Provides
    fun provideConnectivity(@ActivityContext context: Context): RaceConnectivityManager = RaceConnectivityManager(context)
}