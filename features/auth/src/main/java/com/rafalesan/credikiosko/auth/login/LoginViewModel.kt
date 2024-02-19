package com.rafalesan.credikiosko.auth.login

import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.auth.R
import com.rafalesan.credikiosko.core.auth.domain.auth.usecases.LoginUseCase
import com.rafalesan.credikiosko.core.auth.domain.auth.validators.CredentialsValidator
import com.rafalesan.credikiosko.core.auth.domain.auth.validators.CredentialsValidator.CredentialValidation.EMPTY_DEVICE_NAME
import com.rafalesan.credikiosko.core.auth.domain.auth.validators.CredentialsValidator.CredentialValidation.EMPTY_EMAIL
import com.rafalesan.credikiosko.core.auth.domain.auth.validators.CredentialsValidator.CredentialValidation.EMPTY_PASSWORD
import com.rafalesan.credikiosko.core.auth.domain.auth.validators.CredentialsValidator.CredentialValidation.INVALID_EMAIL
import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.core.commons.presentation.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    var deviceName: String? = null
    val formErrors = MutableStateFlow(mutableMapOf<String, Int>())

    private val _action = Channel<LoginAction>(Channel.BUFFERED)
    val action = _action.receiveAsFlow()

    fun perform(event: LoginEvent){
        when(event) {
            LoginEvent.Login         -> login()
            LoginEvent.CreateAccount -> createAccount()
        }
    }

    private fun createAccount() {
        viewModelScope.launch {
            _action.send(LoginAction.OpenSignup)
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
                    _action.send(LoginAction.OpenHome)
                }
                is ResultOf.Failure     -> {
                    handleResultFailure(result)
                }
            }

        }

    }

    private fun handleInvalidDataResult(validations: List<CredentialsValidator.CredentialValidation>) {
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

    private fun handleResultFailure(
        resultFailure: ResultOf.Failure<CredentialsValidator.CredentialValidation>
    ) = viewModelScope.launch {
        when(resultFailure) {
            is ResultOf.Failure.ApiFailure   -> _uiState.send(UiState.ApiError(resultFailure.message))
            ResultOf.Failure.ApiNotAvailable -> _uiState.send(UiState.ApiNotAvailable)
            ResultOf.Failure.NoInternet      -> _uiState.send(UiState.NoInternet)
            ResultOf.Failure.UnknownFailure  -> _uiState.send(UiState.UnknownError)
            is ResultOf.Failure.InvalidData -> handleInvalidDataResult(resultFailure.validations)
        }
    }

    private fun clearFormErrors() {
        viewModelScope.launch {
            formErrors.emit(mutableMapOf())
        }
    }

}
