package com.mcssoft.raceday.domain.usecase.meetings

import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.domain.model.Meeting
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
class GetMeeting @Inject constructor(
    private val iDbRepo: IDbRepo,
    private val externalScope: CoroutineScope
) {
    operator fun invoke(mId: Long): Flow<DataResult<Meeting>> = flow {
        emit(DataResult.loading())

        val meeting = iDbRepo.getMeeting(mId)

        emit(DataResult.success(meeting))
    }.catch { ex ->
        emit(DataResult.failure(ex as Exception))
    }.shareIn(
        scope = externalScope,
        started = SharingStarted.WhileSubscribed() // ,replay = 1
    )
}

// https://developer.android.com/kotlin/flow/stateflow-and-sharedflow#sharein
