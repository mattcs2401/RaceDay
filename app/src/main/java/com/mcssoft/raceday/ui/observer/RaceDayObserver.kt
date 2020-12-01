package com.mcssoft.raceday.ui.observer

import androidx.lifecycle.Observer
import com.mcssoft.raceday.database.entity.RaceMeeting
import com.mcssoft.raceday.viewmodel.RaceDayViewModel

class RaceDayObserver(mainViewModel: RaceDayViewModel) : Observer<List<RaceMeeting>> {

    override fun onChanged(lRaceMeeting: List<RaceMeeting>) {
        //TODO("Not yet implemented")
    }
}
