package com.rafalesan.credikiosko.core.commons.data.utils

import com.rafalesan.credikiosko.core.commons.data.utils.exceptions.ApiException
import com.rafalesan.credikiosko.core.commons.data.utils.exceptions.NoInternetException
import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import java.net.ConnectException
import java.net.UnknownHostException

object ApiResultHandler {

    fun <T, L, R> handle(apiResult: ApiResult<T>, onSuccess: (T) -> ResultOf<L, R>): ResultOf<L, R> {
        return when(apiResult) {
            is ApiResult.Success -> {
                onSuccess.invoke(apiResult.response)
            }
            is ApiResult.Error   -> {
                when(apiResult.exception) {
                    is NoInternetException -> ResultOf.Failure.NoInternet
                    is ApiException        -> ResultOf.Failure.ApiFailure(apiResult.exception.message ?: "",
                                                                           apiResult.exception.errors)
                    is ConnectException,
                    is UnknownHostException -> ResultOf.Failure.ApiNotAvailable

                    else -> ResultOf.Failure.UnknownFailure
                }
            }
        }
    }

}
