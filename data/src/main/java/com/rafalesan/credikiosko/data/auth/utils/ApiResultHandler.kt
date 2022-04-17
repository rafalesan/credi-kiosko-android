package com.rafalesan.credikiosko.data.auth.utils

import com.rafalesan.credikiosko.domain.utils.Result
import java.net.ConnectException
import java.net.UnknownHostException

object ApiResultHandler {

    fun <T, L, R> handle(apiResult: ApiResult<T>, onSuccess: (T) -> Result<L, R>): Result<L, R> {
        return when(apiResult) {
            is ApiResult.Success -> {
                onSuccess.invoke(apiResult.response)
            }
            is ApiResult.Error -> {
                when(apiResult.exception) {
                    is NoInternetException  -> Result.Failure.NoInternet
                    is ApiException         -> Result.Failure.ApiFailure(apiResult.exception.message ?: "",
                                                                         apiResult.exception.errors)
                    is ConnectException,
                    is UnknownHostException -> Result.Failure.ApiNotAvailable

                    else -> Result.Failure.UnknownFailure
                }
            }
        }
    }

}
