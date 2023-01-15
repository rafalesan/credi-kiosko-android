package com.rafalesan.credikiosko.core.commons.presentation.utils

import androidx.annotation.StringRes

sealed class UiState {
    object Idle: UiState()
    class Loading(val isLoading: Boolean,
                  @StringRes val stringResMessageId: Int? = null): UiState()
    object NoInternet: UiState()
    class ApiError(val message: String): UiState()
    object ApiNotAvailable : UiState()
    object UnknownError: UiState()
}
