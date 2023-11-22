package com.mcssoft.raceday.utility.worker

data class WorkerState(
    var status: Status,
//    val isRunnerWorker: Boolean,
//    val isTrainerWorker: Boolean
) {

    companion object {
        // Flow initializer.
        fun initialise(): WorkerState {
            return WorkerState(
                status = Status.Initialise,
//                isRunnerWorker = false,
//                isTrainerWorker = false
            )
        }
    }

    sealed class Status {
        data object Initialise: Status()
        data object Scheduled : Status()
        data object Cancelled : Status()
        data object Failed : Status()
        data object Succeeded : Status()
    }

    fun isTerminalState(state: Status): Boolean {
        return state in listOf(Status.Cancelled, Status.Failed, Status.Succeeded)
    }
}