package com.mcssoft.racedaybasic.domain.usecase.api

import android.util.Log
import com.mcssoft.racedaybasic.data.repository.database.IDbRepo
import com.mcssoft.racedaybasic.data.repository.remote.IRemoteRepo
import com.mcssoft.racedaybasic.data.repository.remote.NetworkResponse.Status
import com.mcssoft.racedaybasic.utility.Constants
import com.mcssoft.racedaybasic.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Class to GET details from the Api.
 * @param iRemoteRepo: Api access.
 */
class SetupBaseFromApi @Inject constructor(
    private val iRemoteRepo: IRemoteRepo,
    private val iDbRepo: IDbRepo,
) {
    /**
     * @param mtgDate: The date to use in the Api Url.
     * @return A Flow of DataResult<Any>.
     */
    operator fun invoke(mtgDate: String): Flow<DataResult<Any>> = flow {
        Log.d("TAG", "SetupBaseFromApi.invoke()")

        try {
            emit(DataResult.loading())

            // GET from the Api (as BaseDto).
            val response = iRemoteRepo.getRaceDay(mtgDate)

            when(response.status) {
                is Status.Exception -> {
                    when(response.ex) {
                        is UnknownHostException -> {
                            emit(DataResult.failure("Unknown Host","Check the network connection."))
                            return@flow
                        }
                        else -> {
                            emit(DataResult.failure(response.ex!!))
                            return@flow
                        }
                    }
                }
                is Status.Error -> {
                    Log.d("TAG","[SetupBaseFromApi] NetworkResponse.Status.Error: ${response.errorCode}")
                    emit(DataResult.error(response.errorCode))
                    return@flow
                }
                is Status.Success -> {
                    // Delete whatever is there (CASCADE should take care of Race/Runner etc).
                    iDbRepo.deleteMeetings()
                }
                else -> {}
            }

            // Save Meeting/Race info.
            response.body.meetings.filter { type ->
                (type.raceType == Constants.MEETING_TYPE) &&
                (type.location in Constants.LOCATIONS) &&
                (!type.venueMnemonic.isNullOrBlank())      // from testing.
            }.forEach { meetingDto ->
                // Note: Scratchings also processed here.
                iDbRepo.insertMeetingWithRaces(meetingDto, meetingDto.races)
            }

            emit(DataResult.success(response.errorCode))
        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

}
