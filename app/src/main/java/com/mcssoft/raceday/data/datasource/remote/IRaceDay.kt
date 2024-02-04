package com.mcssoft.raceday.data.datasource.remote

import com.mcssoft.raceday.domain.dto.BaseDto
import com.mcssoft.raceday.domain.dto.BaseDto2
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IRaceDay {
    /**
     * Get the Api data for Meetings/Races.
     * @param date: The meeting date; as "YYYY-MM-DD".
     * @param jurisdiction: TBA - got to be there but doesn't seem to affect/restrict anything.
     * @return A response anchored at BaseDto (refer ..\domain\dto\BaseDto).
     */
    @GET("{date}/meetings")
    suspend fun getRaceDay(
        @Path("date") date: String,
        @Query("jurisdiction") jurisdiction: String = "QLD"
    ): Response<BaseDto>

    /**
     * Get the Api data for Runners.
     * @param date: The Meeting date; as "YYYY-MM-DD".
     * @param venue: The code of the venue, e.g. SSC.
     * @param raceNo: The Race number.
     * @param jurisdiction: TBA - got to be there but doesn't seem to affect/restrict anything.
     * @return A response anchored at BaseDto2 (refer ..\domain\dto\BaseDto2).
     */
    @GET("{date}/meetings/R/{venue}/races/{raceNo}")
    suspend fun getRunners(
        @Path("date") date: String,
        @Path("venue") venue: String,
        @Path("raceNo") raceNo: String,
        @Query("jurisdiction") jurisdiction: String = "QLD"
    ): Response<BaseDto2>
}
/*
-----
Notes
-----
Examples:

For getRaceDay()
- Meeting and basic Race values.
#https://api.beta.tab.com.au/v1/tab-info-service/racing/dates/2024-02-04/meetings?jurisdiction=QLD

For getRunners()
- Runner values.
#https://api.beta.tab.com.au/v1/tab-info-service/racing/dates/2024-02-04/meetings/R/SSC/races/1?jurisdiction=QLD

Notes:
------
(1) Will need to hit each Race separately to get all Runners.
(2) There is also a URL:
    https://api.beta.tab.com.au/v1/tab-info-service/racing/dates/2024-02-04/meetings/R/SSC/races?jurisdiction=QLD
    but that doesn't give Runner info.
 */
