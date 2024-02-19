package com.mcssoft.raceday.ui.components.runner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.data.repository.preferences.app.PrefsRepo
import com.mcssoft.raceday.ui.components.runner.RunnerState.Status
import com.mcssoft.raceday.utility.Constants.TWENTY_FIVE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RunnerViewModel @Inject constructor(
    private val iDbRepo: IDbRepo,
    prefsRepo: PrefsRepo
) : ViewModel() {

    private val _state = MutableStateFlow(RunnerState.initialise())
    val state = _state.asStateFlow()

    init {
        getRunner(prefsRepo.runnerId)
    }

    /**
     * Get the Runners associated with a Race from the database.
     * @param runnerId: The Runner id (_id value).
     */
    private fun getRunner(runnerId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val runner = iDbRepo.getRunner(runnerId)
                delay(TWENTY_FIVE)
                _state.update { state ->
                    state.copy(
                        exception = null,
                        status = Status.Success,
                        runner = runner
                    )
                }
                _state.emit(state.value)
            } catch(ex: Exception) {
                _state.update { state ->
                    state.copy(
                        exception = ex,
                        status = Status.Failure
                    )
                }
                _state.emit(state.value)
            }
        }
    }
}
