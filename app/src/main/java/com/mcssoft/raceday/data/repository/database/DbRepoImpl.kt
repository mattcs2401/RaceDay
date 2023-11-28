package com.mcssoft.raceday.data.repository.database

import com.mcssoft.raceday.domain.model.Meeting
import com.mcssoft.raceday.domain.model.Race
import com.mcssoft.raceday.domain.model.Runner
import com.mcssoft.raceday.domain.model.Scratching
import com.mcssoft.raceday.domain.model.Summary
import com.mcssoft.raceday.domain.model.Trainer
import com.mcssoft.raceday.domain.dto.TrainerDto
import javax.inject.Inject

class DbRepoImpl @Inject constructor(
    private val dao: IDbRepo
) : IDbRepo {

    override suspend fun deleteAll() {
        return dao.deleteAll()
    }

    //<editor-fold default state="collapsed" desc="Region: Meeting related.">
    override suspend fun insertMeeting(meeting: Meeting): Long {
        return dao.insertMeeting(meeting)
    }

    override suspend fun getMeeting(mId: Long): Meeting {
        return dao.getMeeting(mId)
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
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: Race related.">
    override suspend fun insertRaces(races: List<Race>): List<Long> {
        return dao.insertRaces(races)
    }

    override suspend fun getRaces(mtgId: Long): List<Race> {
        return dao.getRaces(mtgId)
    }

    override suspend fun getRace(rId: Long): Race {
        return dao.getRace(rId)
    }

    override suspend fun getRaceIdByVenueCodeAndRaceNo(venueMnemonic: String, raceNumber: Int): Long {
        return dao.getRaceIdByVenueCodeAndRaceNo(venueMnemonic, raceNumber)
    }
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: Runner related.">
    override suspend fun insertRunners(runners: List<Runner>): List<Long> {
        return dao.insertRunners(runners)
    }

    override suspend fun getRunner(runnerId: Long): Runner {
        return dao.getRunner(runnerId)
    }

    override suspend fun getRunners(raceId: Long): List<Runner> {
        return dao.getRunners(raceId)
    }

    override suspend fun updateRunner(runner: Runner) {
        dao.updateRunner(runner)
    }

    override suspend fun updateRunnerAsScratched(runner: Runner) {
        dao.updateRunnerAsScratched(runner)
    }

    override suspend fun updateRunnerForChecked(race: Race, runner: Runner) {
        return dao.updateRunnerForChecked(race, runner)
    }

    override suspend fun getSummaryByRunner(rName: String, rNumber: Int): Summary? {
        return dao.getSummaryByRunner(rName, rNumber)
    }
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: Summary related.">
    override suspend fun getSummaries(): List<Summary> {
        return dao.getSummaries()
    }

    override suspend fun insertSummary(summary: Summary): Long {
        return dao.insertSummary(summary)
    }

    override suspend fun insertSummaries(summaries: List<Summary>): List<Long> {
        return dao.insertSummaries(summaries)
    }

    override suspend fun deleteSummary(id: Long): Int {
        return dao.deleteSummary(id)
    }

    override suspend fun deleteSummaries(): Int {
        return dao.deleteSummaries()
    }

    override suspend fun deleteSummary(summary: Summary) {
        return dao.deleteSummary(summary)
    }
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: Scratching related.">
    override suspend fun insertScratchings(scratchings: List<Scratching>): List<Long> {
        return dao.insertScratchings(scratchings)
    }

    override suspend fun deleteScratchings(): Int {
        return dao.deleteScratchings()
    }

    override suspend fun getScratchingsForRace(venueMnemonic: String, raceNumber: Int): List<Scratching> {
        return dao.getScratchingsForRace(venueMnemonic, raceNumber)
    }
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: Trainer related.">
    override suspend fun insertTrainer(trainer: Trainer): Long {
        return dao.insertTrainer(trainer)
    }
    override suspend fun getTrainers(): List<Trainer> {
        return dao.getTrainers()
    }

    override suspend fun insertTrainers(trainers: List<Trainer>): List<Long> {
        return dao.insertTrainers(trainers)
    }

//    override suspend fun getTrainersAsDto(trainerNames: String): List<TrainerDto> {
//        return dao.getTrainersAsDto(trainerNames)
//    }

    override suspend fun deleteTrainers(): Int {
        return dao.deleteTrainers()
    }

    //</editor-fold>
}