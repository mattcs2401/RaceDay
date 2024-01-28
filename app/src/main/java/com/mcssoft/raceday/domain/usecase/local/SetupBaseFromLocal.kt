package com.mcssoft.raceday.domain.usecase.local

import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.utility.DataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

// TODO - maybe use this to reload from existing data if some network error ?
class SetupBaseFromLocal @Inject constructor(
    private val iDbRepo: IDbRepo,
    private val externalScope: CoroutineScope
) {
    operator fun invoke(): Flow<DataResult<String>> = flow {
        emit(DataResult.loading())
//
        emit(DataResult.success(""))
    }.catch { ex ->
         emit(DataResult.failure(ex as Exception))
    }.shareIn(
         scope = externalScope,
         started = SharingStarted.WhileSubscribed() // ,replay = 1
    )
}
