package com.mcssoft.raceday.domain.usecase.races

import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.domain.model.Race
import com.mcssoft.raceday.utility.DataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

/**
 * Get a list of Meetings from the database.
 * @param iDbRepo: Database access.
 * @param externalScope: The coroutine scope.
 */
class GetRace @Inject constructor(
    private val iDbRepo: IDbRepo,
    private val externalScope: CoroutineScope
) {
    operator fun invoke(raceId: Long): Flow<DataResult<Race>> = flow {
            emit(DataResult.loading())

            val race = iDbRepo.getRace(raceId)

            emit(DataResult.success(race))
    }.catch { ex ->
        emit(DataResult.failure(ex as Exception))
    }.shareIn(
        scope = externalScope,
        started = SharingStarted.WhileSubscribed() // ,replay = 1
    )
}
