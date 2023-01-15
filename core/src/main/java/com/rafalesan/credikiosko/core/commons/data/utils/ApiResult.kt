package com.rafalesan.credikiosko.core.commons.data.utils

sealed class ApiResult <out T> {
    class Success<out T>(val response: T): ApiResult<T>()
    class Error(val exception: Exception): ApiResult<Nothing>()
}