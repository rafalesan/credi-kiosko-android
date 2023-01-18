package com.rafalesan.credikiosko.auth.signup

import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.auth.R
import com.rafalesan.credikiosko.core.auth.domain.auth.usecases.SignupUseCase
import com.rafalesan.credikiosko.core.auth.domain.auth.validators.SignupValidator
import com.rafalesan.credikiosko.core.auth.domain.auth.validators.SignupValidator.SignupValidation.*
import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.core.commons.presentation.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUseCase: SignupUseCase
) : BaseViewModel() {

    val username = MutableStateFlow("")
    val nickname = MutableStateFlow("")
    val businessName = MutableStateFlow("")
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val passwordConfirmation = MutableStateFlow("")
    var deviceName: String = "Generic Device"
    val formErrors = MutableStateFlow(mutableMapOf<String, Int>())
    val apiFormErrors = MutableStateFlow(mutableMapOf<String, String?>())

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
            _uiState.send(UiState.Loading(true,
                                          R.string.registering))

            val result = signupUseCase(signupData)

            _uiState.send(UiState.Loading(false))

            when(result) {
                is ResultOf.Success     -> {
                    toast(result.value.name)
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

    private fun handleInvalidDataResult(invalidDataResult: ResultOf.InvalidData<SignupValidator.SignupValidation>) {
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

    private fun handleResultFailure(resultFailure: ResultOf.Failure) = viewModelScope.launch {
        when(resultFailure) {
            is ResultOf.Failure.ApiFailure   -> {
                _uiState.send(UiState.ApiError(resultFailure.message))
                showErrorsFromApi(resultFailure.errors)
            }
            ResultOf.Failure.ApiNotAvailable -> _uiState.send(UiState.ApiNotAvailable)
            ResultOf.Failure.NoInternet      -> _uiState.send(UiState.NoInternet)
            ResultOf.Failure.UnknownFailure  -> _uiState.send(UiState.UnknownError)
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
