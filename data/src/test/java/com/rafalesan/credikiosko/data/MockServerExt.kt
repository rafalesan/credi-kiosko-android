package com.rafalesan.credikiosko.data

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

fun MockWebServer.enqueue(jsonFile: String, responseCode: Int = 200) {
    val source = FileResourceHelper.getBufferedSourceOf(jsonFile)
    val mockResponse = MockResponse()
    mockResponse.setResponseCode(responseCode)
    mockResponse.setBody(source.readString(Charsets.UTF_8))
    this.enqueue(mockResponse)
}