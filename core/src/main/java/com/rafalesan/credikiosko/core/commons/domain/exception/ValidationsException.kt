package com.rafalesan.credikiosko.core.commons.domain.exception

class ValidationsException(
    val validations: List<Enum<*>>
) : Exception(validations.toString())
