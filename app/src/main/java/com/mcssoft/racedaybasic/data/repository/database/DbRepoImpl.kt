package com.mcssoft.racedaybasic.data.repository.database

import com.mcssoft.racedaybasic.domain.model.Meeting
import com.mcssoft.racedaybasic.domain.model.MeetingIdsTuple
import com.mcssoft.racedaybasic.domain.model.Race
import com.mcssoft.racedaybasic.domain.model.Runner
import com.mcssoft.racedaybasic.domain.model.Summary
import javax.inject.Inject

class DbRepoImpl @Inject constructor(
    private val dao: IDbRepo
) : IDbRepo {

    //<editor-fold default state="collapsed" desc="Region: MeetingDto related.">
    override suspend fun insertMeeting(meeting: Meeting): Long {
        return dao.insertMeeting(meeting)
    }

    override suspend fun getMeeting(mId: Long): Meeting {
        return dao.getMeeting(mId)
    }

    override suspend fun getMeetings(): List<Meeting> {
        return dao.getMeetings()
    }

    override suspend fun getMeetingId(mId: Int): Long {
        return dao.getMeetingId(mId)
    }

    override suspend fun getMeetingIds(): List<MeetingIdsTuple> {
        return dao.getMeetingIds()
    }

    override suspend fun deleteMeetings(): Int {
        return dao.deleteMeetings()
    }

    override suspend fun meetingCount(): Int {
        return dao.meetingCount()
    }
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: RaceDto related.">
    override suspend fun insertRaces(races: List<Race>): List<Long> {
        return dao.insertRaces(races)
    }

    override suspend fun getRaces(mtgId: Long): List<Race> {
        return dao.getRaces(mtgId)
    }

    override suspend fun getRace(rId: Long): Race {
        return dao.getRace(rId)
    }

//    override suspend fun getRaceId(mtgId: Long, raceNo: Int): Long {
//        return dao.getRaceId(mtgId, raceNo)
//    }
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

    override suspend fun setRunnerChecked(runnerId: Long, checked: Boolean) {
        return dao.setRunnerChecked(runnerId, checked)
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

//    override suspend fun deleteSummary(id: Long) {
//        dao.deleteSummary(id)
//    }
    //</editor-fold>

//    override suspend fun loadTrainerHorses(): Map<Trainer, List<Horse>> {
//        return dao.loadTrainerHorses()
//    }
//
//    override suspend fun getTrainerCount(): Int {
//        return dao.getTrainerCount()
//    }
//
//    override suspend fun insertTrainer(entity: Trainer) {
//        return dao.insertTrainer(entity)
//    }
}