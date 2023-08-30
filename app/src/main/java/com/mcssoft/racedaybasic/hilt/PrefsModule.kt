package com.mcssoft.racedaybasic.hilt

import com.mcssoft.racedaybasic.data.repository.preferences.IPreferences
import com.mcssoft.racedaybasic.data.repository.preferences.Preferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)  // TBA.
@Module
abstract class PrefsModule {

    @Binds
    abstract fun bindUserPreferences(pref: Preferences): IPreferences

}