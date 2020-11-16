package com.mcssoft.raceday.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import javax.inject.Inject
import javax.inject.Singleton

class RaceConnectivityManager @Inject constructor (private val context: Context) {

    private val connMgr: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

//    /**
//     * TBA - deprecated in API 29, but this app targets API 27 as min (Oppo phone ver).
//     */
//    fun isNetworkConnected(): Boolean {
//        return if(connMgr.activeNetworkInfo != null) {
//            connMgr.activeNetworkInfo!!.isConnected
//        } else {
//            false
//        }
//    }

    /**
     * Simple check that there is a network connection.
     */
    private fun isNetworkConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    /**
     * Get the network transport type.
     * @return Type as : NETWORK_MOB, NETWORK_WIFI or NETWORK_NONE.
     * Note: If WiFi and Mob are both selected, then WiFi seems to take preference on the system.
     *       That's possibly ok ?
     */
    fun getTransport() : Int {
        if(isNetworkConnected()) {
            if (networkCapabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return Constants.NETWORK_MOB
            } else if (networkCapabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return Constants.NETWORK_WIFI
            }
        }
        return Constants.NETWORK_NONE
    }

    private val networkCapabilities: NetworkCapabilities?
        get() = connMgr.getNetworkCapabilities(connMgr.activeNetwork)

}
/*
Doco:
-----
https://developer.android.com/reference/android/net/NetworkCapabilities
 */
