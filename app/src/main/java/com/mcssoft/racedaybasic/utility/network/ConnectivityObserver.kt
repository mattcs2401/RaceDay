package com.mcssoft.racedaybasic.utility.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/*
TBA - Based largely on https://www.youtube.com/watch?v=TzV0oCRDNfM
 */
class ConnectivityObserver @Inject constructor(
    val context: Context
): IConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<ConnectivityState.Status> {
        return callbackFlow {
            val callBack = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    trySend(ConnectivityState.Status.Available)
                }
                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    trySend(ConnectivityState.Status.Losing)
                }
                override fun onLost(network: Network) {
                    super.onLost(network)
                    trySend(ConnectivityState.Status.Lost)
                }
                override fun onUnavailable() {
                    super.onUnavailable()
                    trySend(ConnectivityState.Status.Unavailable)
                }
            }
            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build()

            connectivityManager.registerNetworkCallback(request, callBack)

            awaitClose {
                connectivityManager.unregisterNetworkCallback(callBack)
            }
        }
        .distinctUntilChanged()
        .flowOn(Dispatchers.IO)
    }
}