package com.mcssoft.raceday.domain.usecase.local

import com.mcssoft.raceday.data.repository.database.IDbRepo
import javax.inject.Inject

class SetupBaseFromLocal@Inject constructor(
    private val iDbRepo: IDbRepo) {

//    operator fun invoke(): Flow<DataResult<String>> = flow {
//        try {
//            emit(DataResult.loading())
//
//
//            emit(DataResult.success(""))
//        } catch (exception: Exception) {
//            emit(DataResult.failure(exception))
//        }
//    }
}