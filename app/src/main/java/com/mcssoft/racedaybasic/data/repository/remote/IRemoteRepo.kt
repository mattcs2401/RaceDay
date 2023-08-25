package com.mcssoft.racedaybasic.data.repository.remote

import com.mcssoft.racedaybasic.domain.dto.BaseDto

interface IRemoteRepo {

    suspend fun getRaceDay(date: String): NetworkResponse<BaseDto>

}