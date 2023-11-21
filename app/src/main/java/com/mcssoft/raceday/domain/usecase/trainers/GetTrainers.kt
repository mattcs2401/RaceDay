package com.mcssoft.raceday.domain.usecase.trainers

import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.domain.model.Runner
import com.mcssoft.raceday.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTrainers @Inject constructor(
    private val iDbRepo: IDbRepo
) {
    /**
     *
     */
    operator fun invoke(raceId: Long): Flow<DataResult<List<String>>> = flow {
        try {
            emit(DataResult.loading())

            val trainers = iDbRepo.getTrainers(raceId)

            emit(DataResult.success(trainers))

        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

}