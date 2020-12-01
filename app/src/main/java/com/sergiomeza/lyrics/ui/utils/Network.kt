package com.sergiomeza.lyrics.ui.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

object Network {

    fun checkNetworkStateWithActiveNetworkInfo(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo != null && networkInfo.isConnected
        } else
            checkNetworkStateWithNetworkCapabilities(context)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkNetworkStateWithNetworkCapabilities(context: Context): Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) ||
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_VPN) ||
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_LOWPAN)-> true
            else -> false
        }
    }
}