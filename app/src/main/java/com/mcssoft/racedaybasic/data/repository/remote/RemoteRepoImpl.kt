package com.mcssoft.racedaybasic.data.repository.remote

import com.mcssoft.racedaybasic.data.datasource.remote.IRaceDay
import com.mcssoft.racedaybasic.domain.dto.BaseDto
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
                    NetworkResponse.error(result.message())
                }
            }
        } catch (ex: Exception) {
            NetworkResponse.exception(ex)
        }
    }

}