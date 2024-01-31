package com.mcssoft.raceday.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.domain.model.Meeting
import com.mcssoft.raceday.domain.model.Race
import com.mcssoft.raceday.domain.model.Runner
import com.mcssoft.raceday.domain.model.Scratching
import com.mcssoft.raceday.domain.model.Summary

@Database(
    entities = [
        Meeting::class,
        Race::class,
        Scratching::class,
        Runner::class,
        Summary::class
    ],
    version = 2,
    exportSchema = false
)
abstract class RaceDayDb : RoomDatabase() {

    abstract fun getRaceDayDao(): IDbRepo
}
