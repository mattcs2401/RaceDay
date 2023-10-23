package com.mcssoft.racedaybasic.domain.usecase.meetings

import com.mcssoft.racedaybasic.data.repository.database.IDbRepo
import com.mcssoft.racedaybasic.domain.model.Meeting
import com.mcssoft.racedaybasic.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Get a list of Meetings from the database.
 * @param iDbRepo: Database access.
 */
class GetMeeting @Inject constructor(
    private val iDbRepo: IDbRepo
) {
    operator fun invoke(mId: Long): Flow<DataResult<Meeting>> = flow {
        try {
            emit(DataResult.loading())

            val meeting = iDbRepo.getMeeting(mId)

            emit(DataResult.success(meeting))

        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

}
