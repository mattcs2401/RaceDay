package com.mcssoft.racedaybasic.utility.network

import kotlinx.coroutines.flow.Flow

interface IConnectivityObserver {

    fun observe(): Flow<ConnectivityState.Status>

}