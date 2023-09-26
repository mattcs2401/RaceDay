package com.mcssoft.racedaybasic.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mcssoft.racedaybasic.data.repository.database.IDbRepo
import com.mcssoft.racedaybasic.domain.model.Meeting
import com.mcssoft.racedaybasic.domain.model.Race
import com.mcssoft.racedaybasic.domain.model.Runner

@Database(
    entities = [Meeting::class, Race::class, Runner::class],
    version = 1,
    exportSchema = false
)
abstract class RaceDayDb : RoomDatabase() {

    abstract fun getRaceDayDao(): IDbRepo

}