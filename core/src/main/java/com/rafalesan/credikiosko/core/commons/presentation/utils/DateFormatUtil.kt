package com.rafalesan.credikiosko.core.commons.presentation.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

object DateFormatUtil {

    private const val BACKEND_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"

    fun getCurrentDateString(): String {
        val formatter = DateTimeFormatter.ofPattern(BACKEND_DATE_TIME_PATTERN)
        return LocalDateTime.now().format(formatter)
    }

    fun getUIDateFormatFrom(dateString: String): String {
        val localDateTime = getLocalDateTimeFromBackendPattern(dateString)
        val justLocalDate = localDateTime.toLocalDate()
        val dateFormatter = DateTimeFormatter
            .ofLocalizedDate(FormatStyle.LONG)
        return justLocalDate.format(dateFormatter)
    }

    fun getUITimeFormatFrom(dateString: String): String {
        val localDateTime = getLocalDateTimeFromBackendPattern(dateString)
        val justLocalTime = localDateTime.toLocalTime()
        val localizedTimeFormatter = DateTimeFormatter
            .ofPattern("hh:mm a")
        return justLocalTime.format(localizedTimeFormatter)
    }

    private fun getLocalDateTimeFromBackendPattern(dateString: String): LocalDateTime {
        return LocalDateTime.parse(
            dateString,
            DateTimeFormatter.ofPattern(BACKEND_DATE_TIME_PATTERN)
        )
    }

}
