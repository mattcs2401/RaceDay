package com.mcssoft.racedaybasic.domain.usecase.cases.api

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.mcssoft.racedaybasic.data.repository.database.IDbRepo
import com.mcssoft.racedaybasic.data.repository.remote.IRemoteRepo
import com.mcssoft.racedaybasic.utility.DataResult
import com.mcssoft.racedaybasic.utility.worker.RunnersWorker
import com.mcssoft.racedaycompose.utility.WorkerState
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

class SetupRunnersFromApi {

    private lateinit var workManager: WorkManager

    operator fun invoke(context: Context, meetingId: String): Flow<DataResult<Any>> = flow {

        workManager = WorkManager.getInstance(context)

        try {
            emit(DataResult.loading())
//            val workData = workDataOf("key_meeting_id" to meetingId)
            val runnersWorker = OneTimeWorkRequestBuilder<RunnersWorker>()
                .addTag("RunnersWorker")
                .setInputData(workDataOf("key_meeting_id" to meetingId))
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