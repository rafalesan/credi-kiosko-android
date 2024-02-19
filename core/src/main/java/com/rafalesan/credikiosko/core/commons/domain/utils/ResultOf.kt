package com.rafalesan.credikiosko.core.commons.domain.utils

sealed class ResultOf<out L, out R> {
    class Success<out L>(val value: L): ResultOf<L, Nothing>()

    sealed class Failure<out R> : ResultOf<Nothing, R>() {
        class InvalidData<out R>(val validations: List<R>) : Failure<R>()
        class ApiFailure(val message: String = "",
                         val errors: Map<String, List<String>>? = null): Failure<Nothing>()
        data object ApiNotAvailable : Failure<Nothing>()
        data object NoInternet : Failure<Nothing>()
        data object UnknownFailure : Failure<Nothing>()
    }
}
