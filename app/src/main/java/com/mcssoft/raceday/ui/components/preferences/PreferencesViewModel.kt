package com.mcssoft.raceday.ui.components.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.raceday.data.repository.preferences.PrefsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val prefsRepo: PrefsRepo,
) : ViewModel() {

    private val _state = MutableStateFlow(PreferencesState.initialise())
    val state = _state.asStateFlow()

    init {
        _state.update { state ->
            state.copy(
                sourceFromApi = prefsRepo.fromApi
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            _state.emit(state.value)
        }
    }

    fun onEvent(event: PreferencesEvent) {
        when (event) {
            is PreferencesEvent.Checked -> {
                when (event.type) {
                    is PreferencesEvent.EventType.SourceFromApi -> {
                        prefsRepo.fromApi = event.checked
                        _state.update { state ->
                            state.copy(
                                sourceFromApi = prefsRepo.fromApi
                            )
                        }
                        viewModelScope.launch(Dispatchers.IO) {
                            _state.emit(state.value)
                        }
                    }
                }
            }
        }
    }
}
