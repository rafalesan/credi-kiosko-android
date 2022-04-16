package com.rafalesan.credikiosko.presentation.auth.login

import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.domain.auth.usecases.LoginUseCase
import com.rafalesan.credikiosko.domain.utils.Result
import com.rafalesan.credikiosko.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : BaseViewModel() {

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    var deviceName: String? = null
    val formErrors = MutableStateFlow(mutableMapOf<String, Int?>())

    fun onLogin() {
        if(!isFormValid()) {
            return
        }
        val credentials = LoginUseCase.Credentials(email.value,
                                                   password.value,
                                                   deviceName)

        viewModelScope.launch {
            val result = loginUseCase.invoke(credentials)

            when(result) {
                is Result.Success -> {
                    _toast.value = result.value.name
                }
                is Result.Failure -> {
                    _toast.value = result.message
                }
            }

        }

    }

    fun onCreateAccount() {
        _toast.value = "Under construction"
    }

    private fun isFormValid(): Boolean {
        val errorsMap = mutableMapOf(
                "email" to CredentialsValidator.validateEmail(email.value),
                "password" to CredentialsValidator.validatePassword(password.value)
        )
        viewModelScope.launch {
            formErrors.emit(errorsMap)
        }
        errorsMap.values.removeAll(sequenceOf(null))
        return errorsMap.isEmpty()

    }

}
