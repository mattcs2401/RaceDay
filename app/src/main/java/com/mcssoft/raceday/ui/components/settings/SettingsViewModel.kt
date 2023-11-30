package com.mcssoft.raceday.ui.components.settings

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.raceday.data.repository.preferences.user.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPrefs: DataStore<UserPreferences>,
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState.initialise())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            state.value.sourceFromApi = getSourceFromApi()
            state.value.autoAddTrainers = getAutoAddTrainers()
        }
    }

    fun onEvent(event: SettingsEvent) {
        when(event) {
            is SettingsEvent.Checked -> {
                when(event.type) {
                    is SettingsEvent.EventType.SOURCEFROMAPI -> {
                        viewModelScope.launch(Dispatchers.IO) {
                            setSourceFromApi(event.checked)
                        }
                    }
                    is SettingsEvent.EventType.AUTOADDTRAINER -> {
                        viewModelScope.launch(Dispatchers.IO) {
                            setAutoAddTrainers(event.checked)
                        }
                    }
                }

            }
        }
    }

    //<editor-fold default state="collapsed" desc="Region: Preferences methods">
    private suspend fun getSourceFromApi(): Boolean {
        return userPrefs.data.first().sourceFromApi
    }

    private suspend fun setSourceFromApi(newValue: Boolean) {
        userPrefs.updateData { pref ->
            pref.copy(sourceFromApi = newValue)
        }
    }

    private suspend fun getAutoAddTrainers(): Boolean {
        return userPrefs.data.first().autoAddTrainers
    }

    private suspend fun setAutoAddTrainers(newValue: Boolean) {
        userPrefs.updateData { pref ->
            pref.copy(autoAddTrainers = newValue)
        }
    }
    //</editor-fold>
}