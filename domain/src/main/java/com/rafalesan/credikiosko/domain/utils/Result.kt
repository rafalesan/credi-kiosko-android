package com.rafalesan.credikiosko.domain.utils

sealed class Result<out L, out R> {
    class Success<out L>(val value: L): Result<L, Nothing>()
    class InvalidData<out R>(val validations: List<R>): Result<Nothing, R>()
    sealed class Failure : Result<Nothing, Nothing>() {
        class ApiFailure(val message: String = "",
                         val errors: Map<String, List<String>>?): Failure()
        object NoInternet : Failure()
        object UnknownFailure : Failure()
    }
}
