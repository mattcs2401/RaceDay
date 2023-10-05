package com.mcssoft.racedaybasic.data.repository.remote

import com.mcssoft.racedaybasic.domain.dto.BaseDto
import com.mcssoft.racedaybasic.domain.dto2.BaseDto2

interface IRemoteRepo {

    suspend fun getRaceDay(date: String): NetworkResponse<BaseDto>

    suspend fun getRaceDayRunners(date: String, venue: String, raceNo: String): NetworkResponse<BaseDto2>
}