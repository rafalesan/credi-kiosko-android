package com.rafalesan.credikiosko.presentation.auth.login

import androidx.annotation.StringRes

sealed class LoginUiState {
    object Idle: LoginUiState()
    class Loading(val isLoading: Boolean,
                  @StringRes val stringResMessageId: Int? = null): LoginUiState()
    object NoInternet: LoginUiState()
    class ApiError(val message: String): LoginUiState()
    object ApiNotAvailable : LoginUiState()
    object UnknownError: LoginUiState()
}
