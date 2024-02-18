package com.mcssoft.raceday.hilt

import android.content.Context
import android.content.SharedPreferences
import com.mcssoft.raceday.R
import com.mcssoft.raceday.data.repository.preferences.app.PrefsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PrefsModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            context.resources.getString(R.string.app_prefs_file_name),
            Context.MODE_PRIVATE
        )
    }

    @Singleton
    @Provides
    fun providePrefsRepo(context: Context): PrefsRepo {
        return PrefsRepo(provideSharedPreferences(context))
    }
}
