package com.rafalesan.credikiosko.presentation.auth.login

import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.domain.auth.usecases.LoginUseCase
import com.rafalesan.credikiosko.domain.auth.validators.CredentialsValidator
import com.rafalesan.credikiosko.domain.auth.validators.CredentialsValidator.CredentialValidation.*
import com.rafalesan.credikiosko.domain.utils.Result
import com.rafalesan.credikiosko.presentation.R
import com.rafalesan.credikiosko.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.presentation.base.utils.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : BaseViewModel() {

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    var deviceName: String? = null
    val formErrors = MutableStateFlow(mutableMapOf<String, Int>())

    private val _event = Channel<LoginEvent>(Channel.BUFFERED)
    val event = _event.receiveAsFlow()

    fun perform(action: LoginAction){
        when(action) {
            LoginAction.Login         -> login()
            LoginAction.CreateAccount -> createAccount()
        }
    }

    private fun createAccount() {
        viewModelScope.launch {
            _event.send(LoginEvent.OpenSignup)
        }
    }

    private fun login() {

        clearFormErrors()

        val credentials = LoginUseCase.Credentials(email.value,
                                                   password.value,
                                                   deviceName)

        viewModelScope.launch {

            _uiState.send(UiState.Loading(true,
                                          R.string.authenticating))

            val result = loginUseCase.invoke(credentials)

            _uiState.send(UiState.Loading(false))

            when(result) {
                is Result.Success -> {
                    toast(result.value.name)
                }
                is Result.InvalidData -> {
                    handleInvalidDataResult(result)
                }
                is Result.Failure -> {
                    handleResultFailure(result)
                }
            }

        }

    }

    private fun handleInvalidDataResult(invalidDataResult: Result.InvalidData<CredentialsValidator.CredentialValidation>) {
        val validations = invalidDataResult.validations
        val errorsMap = mutableMapOf<String, Int>()
        validations.forEach { validation ->
            when(validation) {
                EMPTY_EMAIL       -> errorsMap["email"] = R.string.val_empty_email
                INVALID_EMAIL     -> errorsMap["email"] = R.string.val_invalid_email
                EMPTY_PASSWORD    -> errorsMap["password"] = R.string.val_empty_password
                EMPTY_DEVICE_NAME -> toast("No se asignó el nombre del dispositivo")
            }
        }
        viewModelScope.launch {
            formErrors.emit(errorsMap)
        }
    }

    private fun handleResultFailure(resultFailure: Result.Failure) = viewModelScope.launch {
        when(resultFailure) {
            is Result.Failure.ApiFailure   -> _uiState.send(UiState.ApiError(resultFailure.message))
            Result.Failure.ApiNotAvailable -> _uiState.send(UiState.ApiNotAvailable)
            Result.Failure.NoInternet      -> _uiState.send(UiState.NoInternet)
            Result.Failure.UnknownFailure  -> _uiState.send(UiState.UnknownError)
        }
    }

    private fun clearFormErrors() {
        viewModelScope.launch {
            formErrors.emit(mutableMapOf())
        }
    }

}
