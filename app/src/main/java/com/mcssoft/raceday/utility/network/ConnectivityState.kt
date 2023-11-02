package com.mcssoft.raceday.utility.network

sealed class ConnectivityState {

    companion object {
        // Flow/State initializer. Assuming connectivity is available.
        fun initialise(): Status {
            return Status.Available
        }
    }
    sealed class Status {
        data object Available : Status()
        data object Unavailable : Status()
        data object Losing : Status()
        data object Lost : Status()
    }
}
