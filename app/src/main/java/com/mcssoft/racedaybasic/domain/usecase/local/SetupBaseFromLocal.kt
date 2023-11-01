package com.mcssoft.racedaybasic.domain.usecase.local

import com.mcssoft.racedaybasic.data.repository.database.IDbRepo
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