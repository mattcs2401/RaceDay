package com.mcssoft.raceday.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mcssoft.raceday.database.entity.RaceMeeting

@Dao
interface IRaceDayDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeeting(meeting: RaceMeeting): Long

    @Query("delete from race_day_details")
    fun deleteAll(): Int

    @Query("select * from race_day_details")
    fun getMeetings(): LiveData<List<RaceMeeting>>

}
