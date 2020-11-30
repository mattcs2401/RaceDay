package com.mcssoft.raceday.viewmodel

import androidx.lifecycle.ViewModel
import com.mcssoft.raceday.repository.RaceDayRepository
import javax.inject.Inject

class RaceDayViewModel @Inject constructor() : ViewModel() {

    @Inject lateinit var raceDayRepository: RaceDayRepository

    val value = raceDayRepository.completableJob


}