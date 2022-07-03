package com.rafalesan.credikiosko.data.utils

import com.rafalesan.credikiosko.data.base.ErrorResponse
import com.rafalesan.credikiosko.data.utils.exceptions.ApiException
import com.rafalesan.credikiosko.data.utils.exceptions.NoInternetException
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.coroutineScope
import retrofit2.Response
import timber.log.Timber

object ApiHandler {

    suspend fun <T> performApiCall(call: suspend () -> Response<T>): ApiResult<T> {

        val isInternetAvailable = ConnectivityHelper.isInternetAvailable()
        if(!isInternetAvailable) {
            return ApiResult.Error(NoInternetException())
        }

        return coroutineScope {
            try {
                val response: Response<T> = call.invoke()
                handleResponse(response)
            } catch (ex: Exception) {
                Timber.e(ex)
                handleException(ex)
            }
        }
    }

    fun <T> handleResponse(response: Response<T>): ApiResult<T> {
        if(!response.isSuccessful) {
            return handleUnsuccessfulResponse(response)
        }
        val responseData = response.body()

        responseData ?: run {
            val apiException = ApiException("Api is returning empty body response",
                                            response.code())
            return ApiResult.Error(apiException)
        }

        return ApiResult.Success(responseData)
    }

    private fun handleException(exception: Exception): ApiResult.Error {
        Timber.e(exception)
        return ApiResult.Error(exception)
    }

    private fun handleUnsuccessfulResponse(response: Response<*>): ApiResult.Error {
        val apiException = getApiExceptionFrom(response)
        Timber.e(apiException)
        return ApiResult.Error(apiException)
    }

    private fun getApiExceptionFrom(response: Response<*>): ApiException {
        val errorBody = response.errorBody()
        errorBody ?: return ApiException("No error message provided by api",
                                         response.code())
        val jsonString = errorBody.string()

        val moshi = Moshi.Builder().build()

        val adapter: JsonAdapter<ErrorResponse> = moshi.adapter(ErrorResponse::class.java)

        val errorResponse = adapter.fromJson(jsonString)

        return ApiException(errorResponse?.message ?: "Unknown error",
                            response.code(),
                            errorResponse?.errors)
    }

}
