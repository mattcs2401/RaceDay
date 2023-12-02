package com.mcssoft.raceday.domain.usecase.api

import android.content.Context
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.lifecycle.asFlow
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.mcssoft.raceday.R
import com.mcssoft.raceday.data.repository.database.IDbRepo
import com.mcssoft.raceday.data.repository.preferences.user.UserPreferences
import com.mcssoft.raceday.utility.DataResult
import com.mcssoft.raceday.utility.worker.RunnersWorker
import com.mcssoft.raceday.utility.worker.WorkerState
import com.mcssoft.raceday.utility.worker.WorkerState.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transformWhile
import kotlinx.coroutines.flow.update
import java.util.UUID
import javax.inject.Inject

class SetupRunnersFromApi  @Inject constructor(
    private val iDbRepo: IDbRepo,
    private val context: Context,
    private val userPrefs: DataStore<UserPreferences>
) {
    private val workManager = WorkManager.getInstance(context)

    private val _state = MutableStateFlow(WorkerState.initialise())
    val state = _state.asStateFlow()

    operator fun invoke(): Flow<DataResult<Any>> = flow {
        try {
            emit(DataResult.loading())

            val meetingSubset = iDbRepo.getMeetingSubset()

            meetingSubset.forEach { item ->

                val date = item.meetingDate
                val code = item.venueMnemonic
                val races = item.numRaces.toString()

                val trainerPref = userPrefs.data.first().autoAddTrainers

                val workData = workDataOf(
                    context.resources.getString(R.string.key_meeting_date) to date,
                    context.resources.getString(R.string.key_meeting_code) to code,
                    context.resources.getString(R.string.key_num_races) to races,
                    // Note: Couldn't seem to use this from the context.resources.getBoolean().
                    // TODO - remove hard coding.
                    "key_trainer_pref" to trainerPref
                )
                val runnersWorker = OneTimeWorkRequestBuilder<RunnersWorker>()
                    .addTag("RunnersWorker")
                    .setInputData(workData)
                    .build()
                workManager.enqueue(runnersWorker)

                observeRunnerWorker(runnersWorker.id).collect { result ->
                    when (result) {
                        Status.Scheduled -> {}
                        Status.Cancelled -> {}
                        Status.Failed -> {
                            throw Exception("Observe runnerWorker failure.")
                        }
                        Status.Succeeded -> {
                            Toast.makeText(context, "Runners for: $code Ok.", Toast.LENGTH_SHORT).show()
                            emit(DataResult.success(""))
                        }
                        else -> {}
                    }
                }
            }
            // Update the Summary with any checked Runners.
            val lRunners = iDbRepo.getCheckedRunners()
            lRunners.forEach { runner ->
                iDbRepo.setForSummary(runner)
            }
        } catch (ex: Exception) {
            emit(DataResult.failure(ex))
        }
    }

    private fun observeRunnerWorker(workerId: UUID): Flow<Status> {
        return workManager.getWorkInfoByIdLiveData(workerId)
            .asFlow()
            .map { workInfo ->
                mapWorkInfoStateToTaskState(workInfo.state)
            }
            .transformWhile { workerState ->
                _state.update { state -> state.copy(status = workerState) }
                emit(workerState)
                // This is to terminate this flow when terminal state is arrived.
                !state.value.isTerminalState(workerState)
            }.distinctUntilChanged()
    }

    private fun mapWorkInfoStateToTaskState(state: WorkInfo.State): Status = when (state) {
        WorkInfo.State.ENQUEUED,
        WorkInfo.State.RUNNING,
        WorkInfo.State.BLOCKED -> Status.Scheduled
        WorkInfo.State.CANCELLED -> Status.Cancelled
        WorkInfo.State.FAILED -> Status.Failed
        WorkInfo.State.SUCCEEDED -> Status.Succeeded
    }
}
/*
-----------------
From Kotlin docs:
-----------------
transformWhile
- Applies transform function to each value of the given flow while this function returns true.

distinctUntilChanged
- Returns flow where all subsequent repetitions of the same value are filtered out.
 */