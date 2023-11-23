package com.mcssoft.raceday.domain.usecase.trainers

import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.domain.model.Trainer
import com.mcssoft.raceday.domain.model.TrainerDto
import com.mcssoft.raceday.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTrainersForSummary @Inject constructor(
    private val iDbRepo: IDbRepo
) {
    /**
     *
     */
    operator fun invoke(): Flow<DataResult<List<Trainer>>> = flow {
        try {
            emit(DataResult.loading())

            // Get what will become the Trainer data into a Dto.
            val trainersDto = iDbRepo.getTrainersAsDto("")

            // Insert the Dto data into the Trainer table.
            iDbRepo.insertTrainersFromDto(trainersDto)

            // Get the Trainer data.
            val trainers = iDbRepo.getTrainers()

            emit(DataResult.success(trainers))

        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

}