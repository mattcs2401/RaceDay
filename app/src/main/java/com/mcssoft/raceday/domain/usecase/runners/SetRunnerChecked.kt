package com.mcssoft.raceday.domain.usecase.runners

import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.domain.model.Runner
import com.mcssoft.raceday.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SetRunnerChecked @Inject constructor(
    private val iDbRepo: IDbRepo
) {
    /**
     *
     */
    operator fun invoke(runner: Runner): Flow<DataResult<String>> = flow {
        try {
//            emit(DataResult.loading())

            iDbRepo.updateRunnerAsChecked(runner)

            emit(DataResult.success(""))

        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

}