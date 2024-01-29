package com.mcssoft.raceday.domain.usecase.summary

import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.domain.model.Summary
import com.mcssoft.raceday.utility.DataResult
import com.mcssoft.raceday.utility.DateUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

/**
 * Get a list of the current Summaries and update flag isPastRaceTime as applicable.
 * @param iDbRepo: Local DB access.
 * @param externalScope: Coroutine scope.
 */
class GetSummaries @Inject constructor(
    private val iDbRepo: IDbRepo,
    private val externalScope: CoroutineScope
) {
    private var lTemp = listOf<Summary>()

    operator fun invoke(): Flow<DataResult<List<Summary>>> = flow {
        emit(DataResult.loading())

        lTemp = iDbRepo.getSummaries()

        if (lTemp.isNotEmpty()) {
            val lSummaries = mutableListOf<Summary>()
            val currentTimeMillis = DateUtils().getCurrentTimeMillis()

            for (summary in lTemp) {
                val raceTime = DateUtils().getCurrentTimeMillis(summary.raceStartTime)

                if (!summary.isPastRaceTime) {
                    if (currentTimeMillis > raceTime) {
                        summary.isPastRaceTime = true

                        iDbRepo.updateSummary(summary)
                    }
                }
            }

            lTemp.partition {
                it.isPastRaceTime
            }.also { pair ->
                lSummaries.addAll(
                    pair.second.sortedBy { it.raceStartTime }
                )
                lSummaries.addAll(
                    pair.first.sortedBy { it.raceStartTime }
                )
            }
            emit(DataResult.success(lSummaries.toList()))
        } else {
            emit(DataResult.success(lTemp)) // an empty list.
        }
    }.catch {
        emit(DataResult.failure(it as Exception))
    }.shareIn(
        scope = externalScope,
        started = SharingStarted.WhileSubscribed() // ,replay = 1
    )
}
