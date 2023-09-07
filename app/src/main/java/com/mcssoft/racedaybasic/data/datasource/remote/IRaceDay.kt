package com.mcssoft.racedaybasic.data.datasource.remote

import com.mcssoft.racedaybasic.domain.dto.BaseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IRaceDay {
    /**
     * Get the api.beta.tab.com.au Api data.
     * @param date: The meeting date; as "YYYY-MM-DD".
     * @param jurisdiction: TBA - got to be there but doesn't seem to affect/restrict anything.
     * @return A response anchored at BaseDto (refer ..\domain\dto\BaseDto).
     * @note Must be called from a coroutine or suspending function.
     */
    @GET("{date}/meetings")
    suspend fun getRaceDay(
        @Path("date") date: String,
        @Query("jurisdiction") jurisdiction: String = "QLD"
    ): Response<BaseDto>

}