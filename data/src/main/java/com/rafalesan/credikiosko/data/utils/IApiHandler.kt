package com.rafalesan.credikiosko.data.utils

import retrofit2.Response

interface IApiHandler {
    suspend fun <T> performApiCall(call: suspend () -> Response<T>): ApiResult<T>
}
