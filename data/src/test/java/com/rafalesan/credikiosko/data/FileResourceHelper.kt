package com.rafalesan.credikiosko.data

import okio.BufferedSource
import okio.buffer
import okio.source

object FileResourceHelper {

    fun getBufferedSourceOf(fileName: String): BufferedSource {
        val classLoader = javaClass.classLoader ?: throw RuntimeException("classLoader is null")
        val inputStream = classLoader.getResourceAsStream(fileName)
            ?: throw RuntimeException("$fileName could not be found in resources")
        return inputStream.source().buffer()
    }

}
