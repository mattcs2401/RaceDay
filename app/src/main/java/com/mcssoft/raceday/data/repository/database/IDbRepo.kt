package com.mcssoft.raceday.data.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mcssoft.raceday.domain.dto.MeetingDto
import com.mcssoft.raceday.domain.dto.RaceDto
import com.mcssoft.raceday.domain.dto.RunnerDto
import com.mcssoft.raceday.domain.dto.toMeeting
import com.mcssoft.raceday.domain.dto.toRace
import com.mcssoft.raceday.domain.dto.toRunner
import com.mcssoft.raceday.domain.dto.toScratching
import com.mcssoft.raceday.domain.model.Meeting
import com.mcssoft.raceday.domain.model.Race
import com.mcssoft.raceday.domain.model.Runner
import com.mcssoft.raceday.domain.model.Scratching
import com.mcssoft.raceday.domain.model.Summary
import com.mcssoft.raceday.utility.DateUtils

@Dao
interface IDbRepo {
// TODO - Can we make better use of joins between tables ?

    @Transaction
    // Note: Any Scratchings are also processed.
    suspend fun insertMeetingAndRaces(meetingDto: MeetingDto, racesDto: List<RaceDto>) {
        // Meeting and Race info.
        val meeting = meetingDto.toMeeting() // for sellCode concat.
        // Set the "Meeting time" as the start of the first Race.
        meeting.meetingTime = DateUtils().getTime(racesDto[0].raceStartTime)

        val meetingId = insertMeeting(meeting)

        racesDto.map { raceDto ->
            raceDto.raceStartTime = DateUtils().getTime(raceDto.raceStartTime)
            raceDto.toRace(meetingId, meeting.sellCode!!, meeting.venueMnemonic!!)
        }.also { races ->
            insertRaces(races)
        }

        // Scratchings info.
        val lScratches = mutableListOf<Scratching>()

        racesDto.forEach { raceDto ->
            // One set of Scratching for one Race.
            raceDto.scratchings.forEach { scratchDto ->
                val scratch = toScratching(
                    meetingDto.venueMnemonic!!,
                    raceDto.raceNumber,
                    scratchDto
                )
                lScratches.add(scratch)
            }
            insertScratchings(lScratches)
            // Reset for next iteration (i.e. different Race and Scratchings).
            lScratches.removeAll(lScratches.toSet())
        }
    }

    @Transaction
    suspend fun deleteAll() {
        deleteMeetings() // cascade should take care of Race and Runner.
        deleteScratchings()
        deleteSummaries()
    }

    // <editor-fold default state="collapsed" desc="Region: Meeting related.">
    data class MeetingSubset(
        val meetingDate: String,
        val venueMnemonic: String,
        val numRaces: Int
    )

    @Query("select meetingDate, venueMnemonic, numRaces from Meeting")
    suspend fun getMeetingSubset(): List<MeetingSubset>

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
     * Delete all from Meetings.
     */
    @Query("delete from Meeting")
    suspend fun deleteMeetings(): Int // CASCADE should take care of Races/Runners etc.

    @Transaction
    @Query("select * from Meeting join Race on Meeting.id = Race.mtgId and Meeting.id = :meetingId")
    suspend fun getMeetingWithRaces(meetingId: Long): Map<Meeting, List<Race>>
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="Region: Race related.">
    /**
     * Insert a list of Races.
     * @param races: The list of Races.
     * @return A list of the _ids of the inserted RaceDto records. Usage is TBA ATT.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRaces(races: List<Race>): List<Long>

    @Query("select * from Race where id = :raceId")
    suspend fun getRace(raceId: Long): Race

    @Query("select * from Race where id = :meetingId")
    suspend fun getRaces(meetingId: Long): List<Race>

    @Query("select * from race where venueMnemonic = :venueMnemonic and raceNumber = :raceNumber")
    suspend fun getRaceByVenueCodeAndRaceNo(venueMnemonic: String, raceNumber: Int): Race

    @Query("update Race set raceStartTime = :raceTime where id = :raceId")
    suspend fun updateRaceTime(raceId: Long, raceTime: String)

    @Transaction
    @Query("select * from Race join Runner on Race.id = Runner.raceId and Race.id = :raceId")
    suspend fun getRaceWithRunners(raceId: Long): Map<Race, List<Runner>>
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="Region: Runner related.">
    /**
     * Insert a list of Runners.
     * @param runners: The list of Runners.
     * @return A list of the _ids of the inserted Runner records. Usage is TBA ATT.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRunners(runners: List<Runner>): List<Long>

    @Transaction
    suspend fun insertRunnersWithRaceId(raceId: Long, lRunners: List<RunnerDto>) {
        lRunners.map { runnerDto ->
            runnerDto.toRunner(raceId)
        }.also { runners ->
            insertRunners(runners)
        }
    }

    @Query("select * from Runner where id = :runnerId")
    suspend fun getRunner(runnerId: Long): Runner

    /**
     * Get a list of Runners based on the id of the associated Race.
     * @param raceId: The RaceDto id (database row id).
     * @return A list of Runners.
     */
    @Query("select * from Runner where raceId= :raceId order by runnerNumber")
    suspend fun getRunners(raceId: Long): List<Runner>

    @Query("update Runner set isChecked = :newValue where id = :runnerId")
    suspend fun updateRunnerAsChecked(runnerId: Long, newValue: Boolean): Int

    @Query("update Runner set isScratched = :newValue where id = :runnerId")
    suspend fun updateRunnerAsScratched(runnerId: Long, newValue: Boolean): Int

    @Query("select * from Runner where isScratched = 0 and isChecked = 0 and raceId = :raceId")
    suspend fun getRunnersNotScratched(raceId: Long): List<Runner>

    @Query("select * from Runner where isScratched = 0 and isChecked = 1")
    suspend fun getCheckedRunners(): List<Runner>
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="Region: Summary related.">
    @Query("select count(*) from Summary")
    suspend fun getSummaryCount(): Int

    @Query("select * from Summary where raceId = :raceId and runnerId = :runnerId")
    suspend fun getSummary(raceId: Long, runnerId: Long): Summary

    @Query("select * from Summary")
    suspend fun getSummaries(): List<Summary>

    @Query("select * from Summary where raceId = :raceId")
    suspend fun getSummaries(raceId: Long): List<Summary>

    @Query("select * from Summary where isPastRaceTime = 0 and isNotified = 0")
    suspend fun getCurrentSummaries(): List<Summary>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSummary(summary: Summary): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSummaries(summaries: List<Summary>): List<Long>

    @Query("delete from Summary")
    suspend fun deleteSummaries(): Int

    @Query("delete from Summary where id = :summaryId")
    suspend fun deleteSummary(summaryId: Long): Int

    @Update
    suspend fun updateSummary(summary: Summary): Int

    @Query("update Summary set raceStartTime = :time where id = :summaryId")
    suspend fun updateSummaryTime(summaryId: Long, time: String)
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="Region: Scratching related.">
    @Insert
    suspend fun insertScratchings(scratchings: List<Scratching>): List<Long>

    @Query("delete from Scratching")
    suspend fun deleteScratchings(): Int

    @Query("select * from Scratching where venueMnemonic = :venueMnemonic and raceNumber = :raceNumber")
    suspend fun getScratchingsForRace(venueMnemonic: String, raceNumber: Int): List<Scratching>
    // </editor-fold>
}
