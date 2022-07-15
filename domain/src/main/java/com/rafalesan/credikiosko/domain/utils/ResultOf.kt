package com.rafalesan.credikiosko.domain.utils

sealed class ResultOf<out L, out R> {
    class Success<out L>(val value: L): ResultOf<L, Nothing>()
    class InvalidData<out R>(val validations: List<R>): ResultOf<Nothing, R>()
    sealed class Failure : ResultOf<Nothing, Nothing>() {
        class ApiFailure(val message: String = "",
                         val errors: Map<String, List<String>>? = null): Failure()
        object ApiNotAvailable : Failure()
        object NoInternet : Failure()
        object UnknownFailure : Failure()
    }
}
