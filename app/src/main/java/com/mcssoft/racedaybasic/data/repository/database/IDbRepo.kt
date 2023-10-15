package com.mcssoft.racedaybasic.data.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.mcssoft.racedaybasic.domain.dto.MeetingDto
import com.mcssoft.racedaybasic.domain.dto.RaceDto
import com.mcssoft.racedaybasic.domain.dto.toMeeting
import com.mcssoft.racedaybasic.domain.dto.toRace
import com.mcssoft.racedaybasic.domain.model.Meeting
import com.mcssoft.racedaybasic.domain.model.Race
import com.mcssoft.racedaybasic.domain.model.Runner
import com.mcssoft.racedaybasic.domain.model.Summary
import com.mcssoft.racedaybasic.utility.DateUtils

@Dao
interface IDbRepo {

    @Transaction
    suspend fun insertMeetingWithRaces(meeting: MeetingDto, races: List<RaceDto>) {
        val meetingId = insertMeeting(meeting.toMeeting())
        val racesWithMeetingId  = races.map { raceDto ->
            raceDto.raceStartTime = DateUtils().getTime(raceDto.raceStartTime)
            raceDto.toRace(meetingId)
        }
        insertRaces(racesWithMeetingId)
    }

    //<editor-fold default state="collapsed" desc="Region: MeetingDto related.">
    /**
     * Insert a MeetingDto.
     * @param meeting: The MeetingDto to insert.
     * @return The _id of the inserted MeetingDto record.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeeting(meeting: Meeting): Long

    /**
     * Select all from Meetings.
     * @return A list of MeetingDto.
     */
    @Query("select * from Meeting")
    suspend fun getMeetings(): List<Meeting>

    /**
     * Get a MeetingDto based on it's row id.
     * @param mId: The MeetingDto id.
     * @return A MeetingDto.
     */
    @Query("select * from Meeting where _id = :mId")
    suspend fun getMeeting(mId: Long): Meeting

    /**
     * Get a Meeting's id (record's row id) by the MeetingId value.
     * @param mId: The Meeting's MeetingId.
     * @return A MeetingDto.
     */
    @Query("select _id from Meeting where meetingId = :mId")
    suspend fun getMeetingId(mId: Int): Long


//    @Query("select meetingCode from MeetingDto")
//    suspend fun getMeetingCodes(): List<String>

    /**
     * Delete all from Meetings.
     */
    @Query("delete from Meeting")
    suspend fun deleteMeetings(): Int       // CASCADE should take care of Races/Runners etc.

    /**
     * Get a count of the MeetingDto records.
     */
    @Query("select count(*) from Meeting")
    suspend fun meetingCount(): Int
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: RaceDto related.">
    /**
     * Insert a list of Races.
     * @param races: The list of Races.
     * @return A list of the _ids of the inserted RaceDto records. Usage is TBA ATT.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRaces(races: List<Race>): List<Long>

    /**
     * Get a listing of the Races based on their (foreign key) MeetingDto id.
     * @param mtgId: The Meeting id (database row number).
     * @return A list of Races.
     */
    @Query("select * from race where mtgId= :mtgId")
    suspend fun getRaces(mtgId: Long): List<Race>

    @Query("select * from Race where _id= :rId")
    suspend fun getRace(rId: Long): Race

//    /**
//     * Get the id (database row id) of a RaceDto based on the (foreign key) MeetingDto id and RaceDto number.
//     * @param mtgId: The MeetingDto id (database row number).
//     * @param raceNo: The RaceDto number.
//     * @return A the RaceDto id.
//     */
//    @Query("select _id from RaceDto where mtgId= :mtgId and raceNumber= :raceNo")
//    suspend fun getRaceId(mtgId: Long, raceNo: Int): Long
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: Runner related.">
    /**
     * Insert a list of Runners.
     * @param runners: The list of Runners.
     * @return A list of the _ids of the inserted Runner records. Usage is TBA ATT.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRunners(runners: List<Runner>): List<Long>

    @Query("select * from Runner where _id = :runnerId")
    suspend fun getRunner(runnerId: Long): Runner

    /**
     * Get a list of Runners based on the id of the associated RaceDto.
     * @param raceId: The RaceDto id (database row id).
     * @return A list of Runners.
     */
    @Query("select * from Runner where raceId= :raceId")
    suspend fun getRunners(raceId: Long): List<Runner>

    @Query("update Runner set checked= :checked where _id= :runnerId")
    suspend fun setRunnerChecked(runnerId: Long, checked: Boolean)
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: Summary related.">
    @Query("select * from Summary")
    suspend fun getSummaries(): List<Summary>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSummary(summary: Summary): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSummaries(summaries: List<Summary>): List<Long>

//    @Query("delete from Summary where rrid = :id")
//    suspend fun deleteSummary(id: Long)
    //</editor-fold>
//
//    @Query("select count(*) from Trainer")
//    suspend fun getTrainerCount(): Int
//
//    @Query("select * from Trainer join Horse on Trainer._id = Horse.tId")
//    suspend fun loadTrainerHorses(): Map<Trainer, List<Horse>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertTrainer(entity: Trainer)

}