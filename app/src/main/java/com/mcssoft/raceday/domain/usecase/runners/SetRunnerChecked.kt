package com.mcssoft.raceday.domain.usecase.runners

import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.domain.dto.SummaryDto
import com.mcssoft.raceday.domain.dto.toSummary
import com.mcssoft.raceday.domain.model.Race
import com.mcssoft.raceday.domain.model.Runner
import com.mcssoft.raceday.utility.DataResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SetRunnerChecked @Inject constructor(
    private val iDbRepo: IDbRepo
) {
    /**
     *
     */
    operator fun invoke(race: Race, runner: Runner): Flow<DataResult<String>> = flow {
        try {
            emit(DataResult.loading())

            updateRunnerForChecked(race, runner)

            emit(DataResult.success(""))

        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

    private suspend fun updateRunnerForChecked(race: Race, runner: Runner) {
        iDbRepo.updateRunner(runner)

        if(runner.isChecked) {
            val summaryDto = SummaryDto(
                race._id,
                runner._id,
                race.sellCode,
                race.venueMnemonic,
                race.raceNumber,
                race.raceStartTime,
                runner.runnerNumber,
                runner.runnerName,
                runner.riderDriverName,
                runner.trainerName
            )
            iDbRepo.insertSummary(summaryDto.toSummary())
        } else {
            // Was checked, now unchecked, so remove Summary item.
            iDbRepo.getSummary(race._id, runner._id).let { summary ->
                iDbRepo.deleteSummary(summary._id)
            }
        }
    }

}
