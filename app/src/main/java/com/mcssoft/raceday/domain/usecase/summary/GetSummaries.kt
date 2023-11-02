package com.mcssoft.raceday.domain.usecase.summary

import com.mcssoft.raceday.data.repository.database.IDbRepo
import javax.inject.Inject

class GetSummaries @Inject constructor(
    private val iDbRepo: IDbRepo
)