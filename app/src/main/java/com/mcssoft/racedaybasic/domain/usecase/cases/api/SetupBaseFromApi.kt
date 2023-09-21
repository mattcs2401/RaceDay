package com.mcssoft.racedaybasic.domain.usecase.cases.api

import android.net.http.HttpException
import android.util.Log
import com.mcssoft.racedaybasic.data.repository.database.IDbRepo
import com.mcssoft.racedaybasic.data.repository.remote.IRemoteRepo
import com.mcssoft.racedaybasic.data.repository.remote.NetworkResponse
import com.mcssoft.racedaybasic.domain.dto.MeetingDto
import com.mcssoft.racedaybasic.domain.dto.RaceDto
import com.mcssoft.racedaybasic.domain.dto.toMeeting
import com.mcssoft.racedaybasic.domain.dto.toRace
import com.mcssoft.racedaybasic.domain.model.Race
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
     * @return A Flow of DataResult<String>.
     * @notes If Meeting code is not used, then all Meetings are returned in the RaceDayDto details,
     *        else, just the one Meeting.
     */
    operator fun invoke(mtgDate: String): Flow<DataResult<Any>> = flow {
        Log.d("TAG", "SetupBaseFromApi.invoke()")

        try {
            emit(DataResult.loading())

            // GET from the Api (as BaseDto).
            val response = iRemoteRepo.getRaceDay(mtgDate)

            when(response.status) {
                is NetworkResponse.Status.Exception -> {
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
                is NetworkResponse.Status.Error -> {
                    Log.d("TAG","[SetupBaseFromApi] NetworkResponse.Status.Error: ${response.errorCode}")
                    emit(DataResult.error(response.errorCode))
                    return@flow
                }
                is NetworkResponse.Status.Success -> {
                    // Delete whatever is there (CASCADE should take care of Race/Runner etc).
                    iDbRepo.deleteMeetings()
                }
            }

            // Save Meeting/Race info.
            response.body.meetings.filter { type ->
                (type.raceType == Constants.MEETING_TYPE) && (type.location in Constants.LOCATIONS)
            }.forEach { meetingDto ->
                // Write Meeting details.
                val mId = populateMeeting(meetingDto)
                // Write Race details.
                populateRaces(mId, meetingDto.races)
            }

            emit(DataResult.success(response.errorCode))
        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

    /**
     * Populate (insert) a Meeting into the database.
     * @param meetingDto: The Meeting to insert (mapped from Dto domain to Model by extension
     *                    function on the Dto object).
     * @return The row _id of the inserted Meeting.
     */
    private suspend fun populateMeeting(meetingDto: MeetingDto): Long {
        // Initial create of the Meeting.
        val meeting = meetingDto.toMeeting()
        // Insert.
        return iDbRepo.insertMeeting(meeting)
    }

    /**
     * Populate (bulk insert) a list of Races into the database.
     * @param mId: The Meeting row _id from the Meeting's previous insert.
     * @param dtoRaces: The list of Races to insert (mapped from Dto domain to Model by extension
     *                  function on the Dto object).
     * @return A list of the row _ids of the inserted Races (usage TBA)
     */
    private suspend fun populateRaces(mId: Long, dtoRaces: List<RaceDto>): List<Long> {
        val lRaces = mutableListOf<Race>()
        dtoRaces.forEach { dtoRace ->
            val race = dtoRace.toRace(mId)
//            race.raceTime = DateUtils().getTime(dtoRace.RaceTime)
            lRaces.add(race)
        }
        return iDbRepo.insertRaces(lRaces)
    }

}
