package com.mcssoft.racedaybasic.domain.usecase.cases.api

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.mcssoft.racedaybasic.R
import com.mcssoft.racedaybasic.data.repository.database.IDbRepo
import com.mcssoft.racedaybasic.utility.DataResult
import com.mcssoft.racedaybasic.utility.worker.RunnersWorker
import com.mcssoft.racedaycompose.utility.WorkerState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transformWhile
import java.util.UUID
import javax.inject.Inject

class SetupRunnersFromApi  @Inject constructor(
    private val iDbRepo: IDbRepo,
    private val context: Context
) {
    private val workManager = WorkManager.getInstance(context)

    operator fun invoke(): Flow<DataResult<Any>> = flow {
        try {
            emit(DataResult.loading())

            val meetingSubset = iDbRepo.getMeetingSubset()
            meetingSubset.forEach { item ->
                val date = item.meetingDate
                val code = item.venueMnemonic
                val races = item.numRaces.toString()

                val workData = workDataOf(
                    context.resources.getString(R.string.key_meeting_date) to date,
                    context.resources.getString(R.string.key_meeting_code) to code,
                    context.resources.getString(R.string.key_num_races) to races
                )
                val runnersWorker = OneTimeWorkRequestBuilder<RunnersWorker>()
                    .addTag("RunnersWorker")
                    .setInputData(workData)
                    .build()
                workManager.enqueue(runnersWorker)

                observeRunnerWorker(runnersWorker.id).collect { result ->
                    when (result) {
                        WorkerState.Scheduled -> {}
                        WorkerState.Cancelled -> {}
                        WorkerState.Failed -> {
                            throw Exception("Observe runnerWorker failure.")
                        }

                        WorkerState.Succeeded -> {
                            emit(DataResult.success(""))
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            emit(DataResult.failure(ex))
        }
    }

    private fun observeRunnerWorker(workerId: UUID): Flow<WorkerState> {
        return workManager.getWorkInfoByIdLiveData(workerId)
            .asFlow()
            .map { workInfo ->
                mapWorkInfoStateToTaskState(workInfo.state)
            }
            .transformWhile { workerState ->
                emit(workerState)
                // This is to terminate this flow when terminal state is arrived.
                !workerState.isTerminalState
            }.distinctUntilChanged()
    }

    private fun mapWorkInfoStateToTaskState(state: WorkInfo.State): WorkerState = when (state) {
        WorkInfo.State.ENQUEUED,
        WorkInfo.State.RUNNING,
        WorkInfo.State.BLOCKED -> WorkerState.Scheduled
        WorkInfo.State.CANCELLED -> WorkerState.Cancelled
        WorkInfo.State.FAILED -> WorkerState.Failed
        WorkInfo.State.SUCCEEDED -> WorkerState.Succeeded
    }
}
/*
From Kotlin docs:
-----------------
transformWhile
- Applies transform function to each value of the given flow while this function returns true.

distinctUntilChanged
- Returns flow where all subsequent repetitions of the same value are filtered out.

 */