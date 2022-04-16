package com.rafalesan.credikiosko.data.auth.utils

import java.lang.Exception

class ApiException(message: String,
                   var statusCode: Int,
                   var errors: Map<String, List<String>>? = null): Exception(message)
