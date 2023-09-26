package com.mcssoft.racedaycompose.utility

enum class WorkerState {

    Scheduled, Cancelled, Failed, Succeeded;

    val isTerminalState: Boolean get() = this in listOf(Cancelled, Failed, Succeeded)

}