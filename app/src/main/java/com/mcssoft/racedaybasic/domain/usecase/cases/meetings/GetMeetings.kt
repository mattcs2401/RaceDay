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
        Log.d("TAG", "GetMeetings.invoke()")
        try {
            emit(DataResult.loading())

            var meetings = iDbRepo.getMeetings()

//            // Filter if required.
//            if (onlyAuNz) {
//                // TODO - this could possibly still filter out something inadvertently.
//                val value = meetings.filter { meeting ->
//                    meeting.meetingCode.toCharArray()[1] != 'S' || meeting.meetingCode == "ZS"
//                }.also { list ->
//                    // also filter out any abandoned Meetings.
//                    list.filter {
//                        meeting -> !meeting.abandoned
//                    }
//                }
//                meetings = value
//            }

            emit(DataResult.success(meetings))

        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

}
