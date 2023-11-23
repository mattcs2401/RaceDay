package com.mcssoft.raceday.ui.components.trainer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mcssoft.raceday.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TrainerViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle      // TBA.
) : ViewModel() {

    private val _state = MutableStateFlow(TrainerState.initialise())
    val state = _state.asStateFlow()

    init {
//        getSummaries()
    }

    fun onEvent(event: TrainerEvent) {
        // TBA.
    }

//    private fun getSummaries() {
//        viewModelScope.launch(Dispatchers.IO) {
//            useCases.getSummaries().collect { result ->
//                when {
//                    result.loading -> {
//                        _state.update { state ->
//                            state.copy(
//                                exception = null,
//                                status = TrainerState.Status.Loading,
//                                loading = true
//                            )
//                        }
//                    }
//                    result.failed -> {
//                        _state.update { state ->
//                            state.copy(
//                                exception = result.exception,
//                                status = TrainerState.Status.Failure,
//                                loading = false
//                            )
//                        }
//                    }
//                    result.successful -> {
//                        _state.update { state ->
//                            state.copy(
//                                exception = null,
//                                status = TrainerState.Status.Success,
//                                loading = false,
//                                summaries = result.data ?: emptyList()
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }

}