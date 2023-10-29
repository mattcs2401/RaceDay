package com.mcssoft.racedaybasic.data.repository.remote

import com.mcssoft.racedaybasic.domain.dto.BaseDto
import com.mcssoft.racedaybasic.domain.dto.BaseDto2

interface IRemoteRepo {

    suspend fun getRaceDay(date: String): NetworkResponse<BaseDto>

    suspend fun getRaceDayRunners(date: String, venueCode: String, raceNum: String): NetworkResponse<BaseDto2>
}