package com.mcssoft.racedaybasic.data.repository.remote

import android.util.Log
import com.mcssoft.racedaybasic.data.datasource.remote.IRaceDay
import com.mcssoft.racedaybasic.domain.dto.BaseDto
import com.mcssoft.racedaybasic.domain.dto2.BaseDto2
import javax.inject.Inject

/**
 * Class to implement the method that gets from the Api.
 */
class RemoteRepoImpl @Inject constructor(
    private val api: IRaceDay
) : IRemoteRepo {

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

    override suspend fun getRaceDayRunners(date: String, venue: String, raceNo: String): NetworkResponse<BaseDto2> {
        return try {
            val result = api.getRunners(date, venue, raceNo)
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