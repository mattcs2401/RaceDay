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
class GetRaces @Inject constructor(
    private val iDbRepo: IDbRepo
) {
    operator fun invoke(mId: Long): Flow<DataResult<List<Race>>> = flow {
        try {
            emit(DataResult.loading())

            val races = iDbRepo.getRaces(mId)

            emit(DataResult.success(races))

        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

}
