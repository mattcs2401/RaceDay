package com.mcssoft.racedaybasic.domain.usecase.cases.meetings

import android.util.Log
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
class GetMeetings @Inject constructor(
    private val iDbRepo: IDbRepo
) {
    operator fun invoke(): Flow<DataResult<List<Meeting>>> = flow {
//        Log.d("TAG", "GetMeetings.invoke()")
        try {
            emit(DataResult.loading())

            var meetings = iDbRepo.getMeetings()

            meetings = meetings.sortedBy { meeting -> meeting.location }

            emit(DataResult.success(meetings))

        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

}
