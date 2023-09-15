package com.mcssoft.racedaybasic.utility

import android.net.ConnectivityManager
import android.net.Network

interface INetworkManager {

    fun isCapableNetwork(cm: ConnectivityManager, network: Network): Boolean

}