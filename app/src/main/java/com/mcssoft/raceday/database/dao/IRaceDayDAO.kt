package com.mcssoft.raceday.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mcssoft.raceday.database.entity.RaceMeeting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface IRaceDayDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeeting(meeting: RaceMeeting): Long

    @Query("delete from race_day_details")
    fun deleteAll(): Int

    @Query("select * from race_day_details")
    fun getMeetings(): LiveData<List<RaceMeeting>>

//    @Query("select * from race_day_details")
//    fun getMeetingsFlow(): Flow<List<RaceMeeting>>

}
