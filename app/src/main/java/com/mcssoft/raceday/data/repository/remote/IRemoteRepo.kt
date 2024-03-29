package com.mcssoft.raceday.data.repository.remote

import com.mcssoft.raceday.domain.dto.BaseDto
import com.mcssoft.raceday.domain.dto.BaseDto2

typealias MeetingInfo = BaseDto
typealias RunnerInfo = BaseDto2

interface IRemoteRepo {

    suspend fun getRaceDay(date: String): NetworkResponse<MeetingInfo>

    suspend fun getRaceDayRunners(
        date: String,
        venueCode: String,
        raceNum: String
    ): NetworkResponse<RunnerInfo>
}
