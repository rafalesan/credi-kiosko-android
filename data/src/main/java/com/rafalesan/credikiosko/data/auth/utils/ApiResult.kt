package com.rafalesan.credikiosko.data.auth.utils

import java.lang.Exception

sealed class ApiResult <out T> {
    class Success<out T>(val response: T): ApiResult<T>()
    class Error(val exception: Exception): ApiResult<Nothing>()
}