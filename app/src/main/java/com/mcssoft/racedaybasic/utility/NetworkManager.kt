package com.mcssoft.racedaybasic.utility

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

class NetworkManager : INetworkManager {

    override fun isCapableNetwork(cm: ConnectivityManager, network: Network): Boolean {
        cm.getNetworkCapabilities(network)?.also {nc ->
            if(nc.hasTransport((NetworkCapabilities.TRANSPORT_WIFI))) {
                return true
            } else if(nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            }
        }
        return false
    }
}