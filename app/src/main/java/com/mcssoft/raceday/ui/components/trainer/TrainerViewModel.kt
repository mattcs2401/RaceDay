package com.mcssoft.raceday.ui.components.trainer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.raceday.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainerViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle      // TBA.
) : ViewModel() {

    private val _state = MutableStateFlow(TrainerState.initialise())
    val state = _state.asStateFlow()

    init {
        getTrainers()
    }

    fun onEvent(event: TrainerEvent) {
        // TBA.
    }

    private fun getTrainers() {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getTrainersForSummary().collect { result ->
                when {
                    result.loading -> {
                        _state.update { state ->
                            state.copy(
                                exception = null,
                                status = TrainerState.Status.Loading,
                                loading = true
                            )
                        }
                    }
                    result.failed -> {
                        _state.update { state ->
                            state.copy(
                                exception = result.exception,
                                status = TrainerState.Status.Failure,
                                loading = false
                            )
                        }
                    }
                    result.successful -> {
                        _state.update { state ->
                            state.copy(
                                exception = null,
                                status = TrainerState.Status.Success,
                                loading = false,
                                trainers = result.data ?: emptyList()
                            )
                        }
                    }
                }
            }
        }
    }

}