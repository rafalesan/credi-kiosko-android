package com.rafalesan.credikiosko.domain.utils

sealed class Result<out T> {
    class Success<out T>(val value: T): Result<T>()
    class Failure(val failureStatus: FailureStatus,
                  val code: Int? = null,
                  val message: String? = "Unknown error") : Result<Nothing>()
}
