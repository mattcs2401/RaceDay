package com.mcssoft.racedaybasic.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.racedaybasic.data.repository.preferences.IPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale.filter
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferences: IPreferences
) : ViewModel() {

    val fromDbPref: StateFlow<Boolean> = preferences.getFromDbPref()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            false
        )

    /**
     * Save the 'FromDb' preference.
     * @param value: The value to save.
     **/
    fun saveFromDbPref(value: Boolean) {
        viewModelScope.launch {
            preferences.setFromDbPref(value)
        }
    }
}
/*
https://proandroiddev.com/preference-layer-using-data-store-android-jetpack-f47416ea80e2
 */