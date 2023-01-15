package com.rafalesan.credikiosko.core.commons.data.utils

interface IConnectivityHelper {
    suspend fun isInternetAvailable(timeout: Int = 6000): Boolean
}