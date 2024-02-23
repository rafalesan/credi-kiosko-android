package com.rafalesan.credikiosko.home.presentation

import com.rafalesan.credikiosko.core.commons.emptyString

data class HomeState(
    val homeOptions: List<HomeOption> = listOf(),
    val businessName: String = emptyString,
    val appVersion: String = emptyString
)
