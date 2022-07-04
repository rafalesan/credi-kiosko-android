package com.rafalesan.credikiosko.data.utils

interface IConnectivityHelper {
    suspend fun isInternetAvailable(timeout: Int = 6000): Boolean
}