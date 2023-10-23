package com.mcssoft.racedaybasic.domain.usecase.local

import com.mcssoft.racedaybasic.data.repository.database.IDbRepo
import com.mcssoft.racedaybasic.data.repository.remote.IRemoteRepo
import com.mcssoft.racedaybasic.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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