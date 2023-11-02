package com.mcssoft.raceday.domain.usecase.runners

import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.domain.model.Runner
import com.mcssoft.raceday.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRunners @Inject constructor(
    private val iDbRepo: IDbRepo
) {
    /**
     *
     */
    operator fun invoke(raceId: Long): Flow<DataResult<List<Runner>>> = flow {
        try {
            emit(DataResult.loading())

            val runners = iDbRepo.getRunners(raceId)

            emit(DataResult.success(runners))

        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

}