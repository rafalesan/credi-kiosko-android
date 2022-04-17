package com.rafalesan.credikiosko.data.auth.utils

import kotlinx.coroutines.coroutineScope
import org.json.JSONObject
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
        val errorResponse = response.errorBody()
        errorResponse ?: return ApiException("No error message provided by api",
                                             response.code())
        val jsonString = errorResponse.string()
        val errorJsonObj = JSONObject(jsonString)
        val errorMessage = errorJsonObj.optString("message") ?: "No error message provided by api"
        val errorsDetailJsonObj = errorJsonObj.optJSONObject("errors")
        var errorsMap: MutableMap<String, List<String>>? = null
        errorsDetailJsonObj?.let {
            errorsMap = mutableMapOf<String, List<String>>().apply {
                errorsDetailJsonObj.keys().forEach {
                    val errorsList = mutableListOf<String>()
                    val errorsJsonArray = errorsDetailJsonObj.getJSONArray(it)
                    for (i in 0 until errorsJsonArray.length()) {
                        errorsList.add(errorsJsonArray.getString(i))
                    }
                    put(it, errorsList)
                }
            }
        }
        return ApiException(errorMessage,
                response.code(),
                errorsMap)
    }

}
