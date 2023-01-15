package com.rafalesan.credikiosko.core.commons.data.utils.exceptions

class ApiException(message: String,
                   var statusCode: Int,
                   var errors: Map<String, List<String>>? = null): Exception(message)
