package com.mcssoft.raceday.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.mcssoft.raceday.database.entity.RaceMeeting
import com.mcssoft.raceday.repository.RaceDayPreferences
import com.mcssoft.raceday.repository.RaceDayRepository
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@ActivityScoped
class RaceDayViewModel @ViewModelInject constructor(private var repository: RaceDayRepository) : ViewModel() {

    val meetings: LiveData<List<RaceMeeting>>?
        get() = repository.getRaceDayCache()

    fun clearCache() = repository.clearCache()
}
