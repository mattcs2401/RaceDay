package com.mcssoft.racedaybasic.domain.usecase.cases.races

import com.mcssoft.racedaybasic.data.repository.database.IDbRepo
import com.mcssoft.racedaybasic.domain.model.Race
import com.mcssoft.racedaybasic.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Get a list of Races from the database.
 * @param iDbRepo: Database access.
 */
class GetRace @Inject constructor(
    private val iDbRepo: IDbRepo
) {
    operator fun invoke(raceId: Long): Flow<DataResult<Race>> = flow {
        try {
            emit(DataResult.loading())

            val race = iDbRepo.getRace(raceId)

            emit(DataResult.success(race))

        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

}
