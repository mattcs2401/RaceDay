package com.mcssoft.raceday.viewmodel

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.mcssoft.raceday.database.entity.RaceMeeting
import com.mcssoft.raceday.repository.RaceDayRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow

@ActivityScoped
class RaceDayViewModel @ViewModelInject constructor(@ApplicationContext private val context: Context,
                                                    private val repository: RaceDayRepository) : ViewModel() {

    val meetings: LiveData<List<RaceMeeting>>? = repository.raceDayCache
}
