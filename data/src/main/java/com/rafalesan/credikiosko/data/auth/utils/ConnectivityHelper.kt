package com.rafalesan.credikiosko.data.auth.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

@Suppress("BlockingMethodInNonBlockingContext")
object ConnectivityHelper {

    private const val GOOGLE_PAGE_URL = "https://www.google.com"

    suspend fun isInternetAvailable(timeout: Int = 6000) = withContext(Dispatchers.IO) {
        try {
            val urlConnection = URL(GOOGLE_PAGE_URL).openConnection() as HttpURLConnection
            urlConnection.setRequestProperty("User-Agent", "Test")
            urlConnection.setRequestProperty("Connection", "close")
            urlConnection.connectTimeout = timeout
            urlConnection.connect()
            urlConnection.responseCode == 200
        } catch (ex: IOException) {
            Timber.e(ex)
            false
        } catch (ex: SecurityException) {
            Timber.e(ex)
            false
        }
    }
}