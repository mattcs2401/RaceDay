package com.mcssoft.raceday.ui.components.settings

import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.raceday.data.repository.preferences.user.UserPreferences
import com.mcssoft.raceday.domain.usecase.UseCases
import com.mcssoft.raceday.ui.components.trainer.TrainerEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.serializer
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
        }
    }

    fun onEvent(event: SettingsEvent) {
        when(event) {
            is SettingsEvent.Checked -> {
                viewModelScope.launch(Dispatchers.IO) {
                    setSourceFromApi(event.checked)
                }
            }
        }
    }

    private suspend fun getSourceFromApi(): Boolean {
        return userPrefs.data.first().sourceFromApi
    }

    private suspend fun setSourceFromApi(newValue: Boolean) {
        userPrefs.updateData { pref ->
            pref.copy(sourceFromApi = newValue)
        }
    }
}