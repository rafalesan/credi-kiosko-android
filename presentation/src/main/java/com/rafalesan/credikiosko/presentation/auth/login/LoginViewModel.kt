package com.rafalesan.credikiosko.presentation.auth.login

import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val formErrors = MutableStateFlow(mutableMapOf<String, Int?>())

    fun onLogin() {
        if(!isFormValid()) {
            return
        }
        _toast.value = "Login is valid"
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
