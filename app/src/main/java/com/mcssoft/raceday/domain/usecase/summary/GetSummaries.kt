package com.mcssoft.raceday.domain.usecase.summary

import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.domain.model.Summary
import com.mcssoft.raceday.utility.DataResult
import com.mcssoft.raceday.utility.DateUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSummaries @Inject constructor(
    private val iDbRepo: IDbRepo
) {
    /**
     *
     */
    operator fun invoke(): Flow<DataResult<List<Summary>>> = flow {
        try {
            emit(DataResult.loading())

            val lSummaries = iDbRepo.getSummaries()

            val currentTimeMillis = DateUtils().getCurrentTimeMillis()

            for(summary in lSummaries) {
                val raceTime = DateUtils().getCurrentTimeMillis(summary.raceStartTime)

                if(!summary.isPastRaceTime) {
                    if(currentTimeMillis > raceTime) {
                        summary.isPastRaceTime = true

                        iDbRepo.updateSummary(summary)
                    }
                }
            }

            emit(DataResult.success(lSummaries))

        } catch (ex: Exception) {
            emit(DataResult.failure(ex))
        }
    }
}