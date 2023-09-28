package com.ix.cookbook.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class NetworkListener : ConnectivityManager.NetworkCallback() {

    private val isAvailable = MutableStateFlow(false)

    fun register(context: Context): MutableStateFlow<Boolean> {
        val connectivityManager =
            context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        val currentNetwork = connectivityManager.activeNetwork

        val caps = connectivityManager.getNetworkCapabilities(currentNetwork)
        isAvailable.value =
            caps?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
        connectivityManager.registerDefaultNetworkCallback(this)
        return isAvailable
    }

    override fun onAvailable(network: Network) {
        if (!isAvailable.value) {
            isAvailable.update { true }
        }
        super.onAvailable(network)
    }

    override fun onLost(network: Network) {
        if (isAvailable.value) {
            isAvailable.update { false }
        }
        super.onLost(network)
    }
}
