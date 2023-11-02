package com.mcssoft.raceday.domain.usecase.races

import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.domain.model.Race
import com.mcssoft.raceday.utility.DataResult
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
