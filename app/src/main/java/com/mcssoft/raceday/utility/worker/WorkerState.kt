package com.mcssoft.raceday.utility.worker

enum class WorkerState {

    Scheduled, Cancelled, Failed, Succeeded;

    val isTerminalState: Boolean get() = this in listOf(Cancelled, Failed, Succeeded)

}