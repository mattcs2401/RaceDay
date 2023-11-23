package com.mcssoft.raceday.domain.usecase.trainers

import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTrainersForSummary @Inject constructor(
    private val iDbRepo: IDbRepo
) {
    /**
     *
     */
    operator fun invoke(): Flow<DataResult<List<Any>>> = flow {
        try {
            emit(DataResult.loading())

            val trainers = iDbRepo.getTrainers("")

            emit(DataResult.success(trainers))

        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

}