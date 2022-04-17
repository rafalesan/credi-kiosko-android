package com.rafalesan.credikiosko.presentation.auth.signup

import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.domain.auth.usecases.SignupUseCase
import com.rafalesan.credikiosko.domain.auth.validators.SignupValidator
import com.rafalesan.credikiosko.domain.auth.validators.SignupValidator.SignupValidation.*
import com.rafalesan.credikiosko.domain.utils.Result
import com.rafalesan.credikiosko.presentation.R
import com.rafalesan.credikiosko.presentation.base.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignupViewModel(private val signupUseCase: SignupUseCase) : BaseViewModel() {

    val username = MutableStateFlow("")
    val nickname = MutableStateFlow("")
    val businessName = MutableStateFlow("")
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val passwordConfirmation = MutableStateFlow("")
    var deviceName: String = "Generic Device"
    val formErrors = MutableStateFlow(mutableMapOf<String, Int>())
    val apiFormErrors = MutableStateFlow(mutableMapOf<String, String?>())

    private val _uiState = Channel<SignupUiState>(Channel.BUFFERED)
    val uiState = _uiState.receiveAsFlow()

    fun perform(signupAction: SignupAction) {
        when(signupAction){
            SignupAction.Signup -> signup()
        }
    }

    private fun signup() {
        clearFormErrors()
        val signupData = SignupUseCase.SignupData(username.value,
                                                  nickname.value,
                                                  businessName.value,
                                                  email.value,
                                                  password.value,
                                                  passwordConfirmation.value,
                                                  deviceName)
        viewModelScope.launch {
            _uiState.send(SignupUiState.Loading(true,
                                                R.string.registering))

            val result = signupUseCase(signupData)

            _uiState.send(SignupUiState.Loading(false))

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

    private fun handleInvalidDataResult(invalidDataResult: Result.InvalidData<SignupValidator.SignupValidation>) {
        val validations = invalidDataResult.validations
        val errorsMap = mutableMapOf<String, Int>()
        validations.forEach { validation ->
            when(validation) {
                EMPTY_NAME                  -> errorsMap["name"] = R.string.val_empty_full_user_name
                MIN_10_CHARS_NAME           -> errorsMap["name"] = R.string.val_min_10_chars_full_user_name
                EMPTY_BUSINESS_NAME         -> errorsMap["businessName"] = R.string.val_empty_business_name
                MIN_2_CHARS_BUSINESS_NAME   -> errorsMap["businessName"] = R.string.val_min_2_chars_business_name
                EMPTY_NICKNAME              -> errorsMap["nickname"] = R.string.val_empty_nickname
                MIN_3_CHARS_NICKNAME        -> errorsMap["nickname"] = R.string.val_min_3_chars_nickname
                EMPTY_EMAIL                 -> errorsMap["email"] = R.string.val_empty_email
                INVALID_EMAIL               -> errorsMap["email"] = R.string.val_invalid_email
                EMPTY_PASSWORD              -> errorsMap["password"] = R.string.val_empty_password
                EMPTY_PASSWORD_CONFIRMATION -> errorsMap["passwordConfirmation"] = R.string.val_empty_passwrod_confirmation
                PASSWORDS_NOT_EQUALS        -> {
                    errorsMap["password"] = R.string.val_password_not_equals
                    errorsMap["passwordConfirmation"] = R.string.val_password_not_equals
                }
                EMPTY_DEVICE_NAME           -> toast("No se asignó el nombre del dispositivo")
            }
        }
        viewModelScope.launch {
            formErrors.emit(errorsMap)
        }
    }

    private fun handleResultFailure(resultFailure: Result.Failure) = viewModelScope.launch {
        when(resultFailure) {
            is Result.Failure.ApiFailure  -> {
                _uiState.send(SignupUiState.ApiError(resultFailure.message))
                showErrorsFromApi(resultFailure.errors)
            }
            Result.Failure.ApiNotAvailable -> _uiState.send(SignupUiState.ApiNotAvailable)
            Result.Failure.NoInternet      -> _uiState.send(SignupUiState.NoInternet)
            Result.Failure.UnknownFailure  -> _uiState.send(SignupUiState.UnknownError)
        }
    }

    private fun showErrorsFromApi(errors: Map<String, List<String>>?) {
        errors ?: return
        val apiErrorsMap = errors.mapValues {
            it.value.firstOrNull()
        }.toMutableMap()
        viewModelScope.launch {
            apiFormErrors.emit(apiErrorsMap)
        }
    }

    private fun clearFormErrors() {
        viewModelScope.launch {
            formErrors.emit(mutableMapOf())
            apiFormErrors.emit(mutableMapOf())
        }
    }

}
