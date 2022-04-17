package com.rafalesan.credikiosko.presentation.auth.signup

import androidx.annotation.StringRes

sealed class SignupUiState {
    object Idle: SignupUiState()
    class Loading(val isLoading: Boolean,
                  @StringRes val stringResMessageId: Int? = null): SignupUiState()
    object NoInternet: SignupUiState()
    class ApiError(val message: String): SignupUiState()
    object UnknownError: SignupUiState()
}
