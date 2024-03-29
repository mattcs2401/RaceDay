package com.mcssoft.raceday.data.repository.database

import com.mcssoft.raceday.domain.model.Meeting
import com.mcssoft.raceday.domain.model.Race
import com.mcssoft.raceday.domain.model.Runner
import com.mcssoft.raceday.domain.model.Scratching
import com.mcssoft.raceday.domain.model.Summary
import javax.inject.Inject

class DbRepoImpl @Inject constructor(
    private val dao: IDbRepo
) : IDbRepo {

    override suspend fun deleteAll() {
        return dao.deleteAll()
    }

    // <editor-fold default state="collapsed" desc="Region: Meeting related.">
    override suspend fun insertMeeting(meeting: Meeting): Long {
        return dao.insertMeeting(meeting)
    }

    override suspend fun getMeetings(): List<Meeting> {
        return dao.getMeetings()
    }

    override suspend fun getMeetingSubset(): List<IDbRepo.MeetingSubset> {
        return dao.getMeetingSubset()
    }

    override suspend fun deleteMeetings(): Int {
        return dao.deleteMeetings()
    }
    // </editor-fold>

    override suspend fun getMeetingWithRaces(meetingId: Long): Map<Meeting, List<Race>> {
        return dao.getMeetingWithRaces(meetingId)
    }

    // <editor-fold default state="collapsed" desc="Region: Race related.">
    override suspend fun insertRaces(races: List<Race>): List<Long> {
        return dao.insertRaces(races)
    }

    override suspend fun getRace(raceId: Long): Race {
        return dao.getRace(raceId)
    }

    override suspend fun getRaces(meetingId: Long): List<Race> {
        return dao.getRaces(meetingId)
    }

    override suspend fun getRaceByVenueCodeAndRaceNo(venueMnemonic: String, raceNumber: Int): Race {
        return dao.getRaceByVenueCodeAndRaceNo(venueMnemonic, raceNumber)
    }

    override suspend fun updateRaceTime(raceId: Long, raceTime: String) {
        return dao.updateRaceTime(raceId, raceTime)
    }

    override suspend fun getRaceWithRunners(raceId: Long): Map<Race, List<Runner>> {
        return dao.getRaceWithRunners(raceId)
    }
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="Region: Runner related.">
    override suspend fun insertRunners(runners: List<Runner>): List<Long> {
        return dao.insertRunners(runners)
    }

    override suspend fun getRunner(runnerId: Long): Runner {
        return dao.getRunner(runnerId)
    }

    override suspend fun getRunners(raceId: Long): List<Runner> {
        return dao.getRunners(raceId)
    }

    override suspend fun updateRunnerAsScratched(runnerId: Long, newValue: Boolean): Int {
        return dao.updateRunnerAsScratched(runnerId, newValue)
    }

    override suspend fun getRunnersNotScratched(raceId: Long): List<Runner> {
        return dao.getRunnersNotScratched(raceId)
    }

    override suspend fun updateRunnerAsChecked(runnerId: Long, newValue: Boolean): Int {
        return dao.updateRunnerAsChecked(runnerId, newValue)
    }

    override suspend fun getCheckedRunners(): List<Runner> {
        return dao.getCheckedRunners()
    }
    // </editor-fold>

    // <editor-fold default state="collapsed" desc="Region: Summary related.">
    override suspend fun getSummaryCount(): Int {
        return dao.getSummaryCount()
    }

    override suspend fun getSummary(raceId: Long, runnerId: Long): Summary {
        return dao.getSummary(raceId, runnerId)
    }

    override suspend fun getSummaries(): List<Summary> {
        return dao.getSummaries()
    }

    override suspend fun getSummaries(raceId: Long): List<Summary> {
        return dao.getSummaries(raceId)
    }

    override suspend fun getCurrentSummaries(): List<Summary> {
        return dao.getCurrentSummaries()
    }

    override suspend fun insertSummary(summary: Summary): Long {
        return dao.insertSummary(summary)
    }

    override suspend fun insertSummaries(summaries: List<Summary>): List<Long> {
        return dao.insertSummaries(summaries)
    }

    override suspend fun deleteSummary(summaryId: Long): Int {
        return dao.deleteSummary(summaryId)
    }

    override suspend fun deleteSummaries(): Int {
        return dao.deleteSummaries()
    }

    override suspend fun updateSummary(summary: Summary): Int {
        return dao.updateSummary(summary)
    }

    override suspend fun updateSummaryTime(summaryId: Long, time: String) {
        return dao.updateSummaryTime(summaryId, time)
    }

    // </editor-fold>

    // <editor-fold default state="collapsed" desc="Region: Scratching related.">
    override suspend fun insertScratchings(scratchings: List<Scratching>): List<Long> {
        return dao.insertScratchings(scratchings)
    }

    override suspend fun deleteScratchings(): Int {
        return dao.deleteScratchings()
    }

    override suspend fun getScratchingsForRace(venueMnemonic: String, raceNumber: Int): List<Scratching> {
        return dao.getScratchingsForRace(venueMnemonic, raceNumber)
    }
    // </editor-fold>
}