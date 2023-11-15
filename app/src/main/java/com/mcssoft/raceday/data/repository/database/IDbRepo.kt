package com.mcssoft.raceday.data.repository.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mcssoft.raceday.domain.dto.MeetingDto
import com.mcssoft.raceday.domain.dto.RaceDto
import com.mcssoft.raceday.domain.dto.toMeeting
import com.mcssoft.raceday.domain.dto.toRace
import com.mcssoft.raceday.domain.dto.RunnerDto
import com.mcssoft.raceday.domain.dto.toRunner
import com.mcssoft.raceday.domain.dto.toScratching
import com.mcssoft.raceday.domain.model.Meeting
import com.mcssoft.raceday.domain.model.Race
import com.mcssoft.raceday.domain.model.Runner
import com.mcssoft.raceday.domain.model.Scratching
import com.mcssoft.raceday.domain.model.Summary
import com.mcssoft.raceday.domain.model.SummaryDto
import com.mcssoft.raceday.domain.model.toSummary
import com.mcssoft.raceday.utility.DateUtils
import kotlinx.coroutines.delay

@Dao
interface IDbRepo {

    @Transaction
    // Note: Any Scratchings are also processed.
    suspend fun insertMeetingAndRaces(meetingDto: MeetingDto, racesDto: List<RaceDto>) {
        // Meeting and Race info.
        val meeting = meetingDto.toMeeting()     // for sellCode concat.
        val meetingId = insertMeeting(meeting)

        val racesWithMeetingId  = racesDto.map { raceDto ->
            raceDto.raceStartTime = DateUtils().getTime(raceDto.raceStartTime)
            raceDto.toRace(meetingId, meeting.sellCode!!, meeting.venueMnemonic!!)
        }
        insertRaces(racesWithMeetingId)

        // Scratchings info.
        val lScratches = mutableListOf<Scratching>()

        racesDto.forEach { raceDto ->
            // One set of Scratching for one Race.
            raceDto.scratchings.forEach { scratchDto ->
                val scratch = scratchDto.toScratching(meetingDto.venueMnemonic!!, raceDto.raceNumber, scratchDto)
                lScratches.add(scratch)
            }
            insertScratchings(lScratches)
            // Reset for next iteration (i.e. different Race and Scratchings).
            lScratches.removeAll(lScratches)
        }
    }

    @Transaction
    suspend fun deleteAll() {
        deleteMeetings()
        deleteScratchings()
        deleteSummaries()
    }

    //<editor-fold default state="collapsed" desc="Region: MeetingDto related.">
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
     * Get a MeetingDto based on it's row id.
     * @param mId: The MeetingDto id.
     * @return A MeetingDto.
     */
    @Query("select * from Meeting where _id = :mId")
    suspend fun getMeeting(mId: Long): Meeting

    /**
     * Delete all from Meetings.
     */
    @Query("delete from Meeting")
    suspend fun deleteMeetings(): Int       // CASCADE should take care of Races/Runners etc.
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: Race related.">
    /**
     * Insert a list of Races.
     * @param races: The list of Races.
     * @return A list of the _ids of the inserted RaceDto records. Usage is TBA ATT.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRaces(races: List<Race>): List<Long>

    /**
     * Get a listing of the Races based on their (foreign key) Meeting id.
     * @param mtgId: The Meeting id (database row number).
     * @return A list of Races.
     */
    @Query("select * from race where mtgId= :mtgId")
    suspend fun getRaces(mtgId: Long): List<Race>

    @Query("select * from Race where _id= :rId")
    suspend fun getRace(rId: Long): Race

    @Query("select _id from race where venueMnemonic = :venueMnemonic and raceNumber = :raceNumber")
    suspend fun getRaceIdByVenueCodeAndRaceNo(venueMnemonic: String, raceNumber: Int): Long
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: Runner related.">
    /**
     * Insert a list of Runners.
     * @param runners: The list of Runners.
     * @return A list of the _ids of the inserted Runner records. Usage is TBA ATT.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRunners(runners: List<Runner>): List<Long>

    @Transaction
    suspend fun insertRunnersWithRaceId(raceId: Long, runners: List<RunnerDto>) {
        val runnersWithRaceId = runners.map { runnerDto ->
            runnerDto.toRunner(raceId)
        }
        insertRunners(runnersWithRaceId)
    }

    @Query("select * from Runner where _id = :runnerId")
    suspend fun getRunner(runnerId: Long): Runner

    /**
     * Get a list of Runners based on the id of the associated Race.
     * @param raceId: The RaceDto id (database row id).
     * @return A list of Runners.
     */
    @Query("select * from Runner where raceId= :raceId order by runnerNumber")
    suspend fun getRunners(raceId: Long): List<Runner>

    @Transaction
    suspend fun updateRunnerForChecked(race: Race, runner: Runner) {
        updateRunner(runner)
        delay(50)       // TBA
        if(runner.isChecked) {
            val summaryDto = SummaryDto(
                race._id,
                runner._id,
                race.sellCode,
                race.venueMnemonic,
                race.raceNumber,
                race.raceStartTime,
                runner.runnerNumber,
                runner.runnerName
            )
            insertSummary(summaryDto.toSummary())
        } else {
            getSummaryByRunner(race.raceName, runner.runnerNumber)?.let { summary ->
                deleteSummary(summary)
            }
        }
    }

    @Update
    suspend fun updateRunnerAsScratched(runner: Runner) {
        updateRunner(runner)
    }

    @Update
    suspend fun updateRunner(runner: Runner)
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: Summary related.">
    @Query("select * from Summary")
    suspend fun getSummaries(): List<Summary>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSummary(summary: Summary): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSummaries(summaries: List<Summary>): List<Long>

    @Query("select * from Summary where runnerName = :rName and runnerNumber = :rNumber")
    suspend fun getSummaryByRunner(rName: String, rNumber: Int): Summary?

    @Query("delete from Summary")
    suspend fun deleteSummaries(): Int

    @Query("delete from Summary where _id = :id")
    suspend fun deleteSummary(id: Long): Int

    @Delete
    suspend fun deleteSummary(summary: Summary)
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: Scratching related.">
    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScratchings(scratchings: List<Scratching>): List<Long>

    @Query("delete from Scratching")
    suspend fun deleteScratchings(): Int

    @Query("select * from Scratching where venueMnemonic = :venueMnemonic and raceNumber = :raceNumber")
    suspend fun getScratchingsForRace(venueMnemonic: String, raceNumber: Int): List<Scratching>
    //</editor-fold>
}