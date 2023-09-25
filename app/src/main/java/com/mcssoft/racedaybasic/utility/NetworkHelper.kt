package com.mcssoft.racedaybasic.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.internal.Contexts.getApplication
import javax.inject.Inject

class NetworkHelper @Inject constructor(val context: Context) {

    fun hasNetwork(): Boolean {
        val connMgr =
            getApplication(context).getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connMgr.activeNetwork ?: return false
        val capabilities = connMgr.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}