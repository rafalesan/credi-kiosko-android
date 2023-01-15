package com.rafalesan.credikiosko.presentation.auth.login

import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.core.commons.presentation.utils.UiState
import com.rafalesan.credikiosko.domain.auth.usecases.LoginUseCase
import com.rafalesan.credikiosko.domain.auth.validators.CredentialsValidator
import com.rafalesan.credikiosko.domain.auth.validators.CredentialsValidator.CredentialValidation.*
import com.rafalesan.credikiosko.presentation.R
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
                is ResultOf.Success     -> {
                    _event.send(LoginEvent.OpenHome)
                }
                is ResultOf.InvalidData -> {
                    handleInvalidDataResult(result)
                }
                is ResultOf.Failure     -> {
                    handleResultFailure(result)
                }
            }

        }

    }

    private fun handleInvalidDataResult(invalidDataResult: ResultOf.InvalidData<CredentialsValidator.CredentialValidation>) {
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

    private fun handleResultFailure(resultFailure: ResultOf.Failure) = viewModelScope.launch {
        when(resultFailure) {
            is ResultOf.Failure.ApiFailure   -> _uiState.send(UiState.ApiError(resultFailure.message))
            ResultOf.Failure.ApiNotAvailable -> _uiState.send(UiState.ApiNotAvailable)
            ResultOf.Failure.NoInternet      -> _uiState.send(UiState.NoInternet)
            ResultOf.Failure.UnknownFailure  -> _uiState.send(UiState.UnknownError)
        }
    }

    private fun clearFormErrors() {
        viewModelScope.launch {
            formErrors.emit(mutableMapOf())
        }
    }

}
