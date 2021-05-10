package com.picpay.desafio.android.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

class Utils {
    companion object {
        /**
         *  Verifica se o aparelho tem algum tipo de conexão com a internet disponível, caso o [Build.VERSION.SDK_INT] >= [Build.VERSION_CODES.M]
         *  o método [isConnectedToInternetApi23] será chamado, caso contrário [isConnectedToInternetDeprecated] será invocado
         *
         *  @return se existe ou não conectividade com a internet
         */
        fun isConnectedToInternet(context: Context): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return isConnectedToInternetApi23(
                    context
                )
            } else {
                return isConnectedToInternetDeprecated(
                    context
                )
            }
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun isConnectedToInternetApi23(context: Context) : Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
            return false
        }

        private fun isConnectedToInternetDeprecated(context: Context) : Boolean {
            var isConnected = false
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnected)
                isConnected = true
            return isConnected
        }
    }



}