package com.mcssoft.raceday.ui.components.summary

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mcssoft.raceday.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(SummaryState.initialise())
    val state = _state.asStateFlow()

    init {
        val bp = "bp"
    }

    fun onEvent(event: SummaryEvent) {
//        when(event) {
//            is RunnersEvent.Check -> {
//                // TODO - create/update Summary record.
//                setRunnerChecked(event.runner)
//            }
//        }
    }
}