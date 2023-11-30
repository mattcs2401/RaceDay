package com.mcssoft.raceday.ui.components.jockey

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mcssoft.raceday.domain.usecase.UseCases
import com.mcssoft.raceday.ui.components.settings.SettingsEvent
import com.mcssoft.raceday.ui.components.trainer.TrainerEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class JockeyViewModel @Inject constructor(
//    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle      // TBA.
) : ViewModel() {

    private val _state = MutableStateFlow(JockeyState.initialise())
    val state = _state.asStateFlow()

    init {

    }

    fun onEvent(event: JockeyEvent) {
        // TBA.
    }

}