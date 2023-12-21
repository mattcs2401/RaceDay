package com.mcssoft.raceday.domain.usecase.api

import android.content.Context
import android.util.Log
import com.mcssoft.raceday.R
import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.data.repository.remote.IRemoteRepo
import com.mcssoft.raceday.data.repository.remote.NetworkResponse.Status
import com.mcssoft.raceday.utility.Constants
import com.mcssoft.raceday.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Class to GET details from the Api.
 * @param iRemoteRepo: Api access.
 * @param iDbRepo: Local DB access.
 * @param context: For string resources.
 */
class SetupBaseFromApi @Inject constructor(
    private val iRemoteRepo: IRemoteRepo,
    private val iDbRepo: IDbRepo,
    private val context: Context
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
                            emit(DataResult.failure(
                                context.resources.getString(R.string.unknown_host),
                                context.resources.getString(R.string.check_network_conn))
                            )
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
                    // Simply delete whatever is there.
                    iDbRepo.deleteAll()
                }
                //else -> {}
            }

            // Save Meeting/Race info.
            response.body.meetings.filter { type ->
                (type.raceType == Constants.MEETING_TYPE) &&
                (type.location in Constants.LOCATIONS) &&
                (!type.venueMnemonic.isNullOrBlank())      // from testing.
            }.forEach { meetingDto ->
                // Note: Any Scratchings also processed here.
                iDbRepo.insertMeetingAndRaces(meetingDto, meetingDto.races)
            }

            emit(DataResult.success(response.errorCode))
        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

}
