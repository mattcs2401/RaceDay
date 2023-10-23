package com.mcssoft.racedaybasic.domain.usecase.runners

import com.mcssoft.racedaybasic.data.repository.database.IDbRepo
import com.mcssoft.racedaybasic.domain.model.Runner
import com.mcssoft.racedaybasic.utility.DataResult
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