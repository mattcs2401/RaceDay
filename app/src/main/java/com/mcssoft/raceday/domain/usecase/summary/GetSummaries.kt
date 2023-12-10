package com.mcssoft.raceday.domain.usecase.summary

import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.domain.model.Summary
import com.mcssoft.raceday.utility.DataResult
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

            val summaries = iDbRepo.getSummaries()

            emit(DataResult.success(summaries))

        } catch (ex: Exception) {
            emit(DataResult.failure(ex))
        }
    }
}