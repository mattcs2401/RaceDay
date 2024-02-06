package com.mcssoft.raceday.data.repository.remote

import com.mcssoft.raceday.data.datasource.remote.IRaceDay
import com.mcssoft.raceday.domain.dto.BaseDto
import com.mcssoft.raceday.domain.dto.BaseDto2
import javax.inject.Inject

/**
 * Class to implement the method that gets from the Api.
 */
class RemoteRepoImpl @Inject constructor(
    private val api: IRaceDay
) : IRemoteRepo {

    /**
     * Get the base Meeting and Race details.
     * @param date: Today's date format YYYY-MM-DD.
     */
    override suspend fun getRaceDay(date: String): NetworkResponse<BaseDto> {
        return try {
            val result = api.getRaceDay(date)
            when {
                result.isSuccessful -> {
                    NetworkResponse.success(result)
                }
                else -> {
                    NetworkResponse.error(result.code())
                }
            }
        } catch (ex: Exception) {
            NetworkResponse.exception(ex)
        }
    }

    /**
     * Get the Runner details.
     * @param date: Today's date.
     * @param venueCode: Code for the venue.
     * @param raceNum: The Race number.
     */
    override suspend fun getRaceDayRunners(
        date: String,
        venueCode: String,
        raceNum: String
    ): NetworkResponse<BaseDto2> {
        return try {
            val result = api.getRunners(date, venueCode, raceNum)
            when {
                result.isSuccessful -> {
                    NetworkResponse.success(result)
                }
                else -> {
                    NetworkResponse.error(result.code())
                }
            }
        } catch(ex: Exception) {
            NetworkResponse.exception(ex)
        }
    }
}