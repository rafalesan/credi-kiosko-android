package com.rafalesan.credikiosko.presentation.auth

import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.core.auth.domain.auth.usecases.ExistUserSession
import com.rafalesan.credikiosko.core.commons.domain.entity.Theme
import com.rafalesan.credikiosko.core.commons.domain.usecases.ChangeThemeUseCase
import com.rafalesan.credikiosko.core.commons.domain.usecases.GetThemeUseCase
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.presentation.auth.AuthAction.ChangeTheme
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AuthViewModel(getThemeUseCase: GetThemeUseCase,
                    private val setThemeUseCase: ChangeThemeUseCase,
                    private val existUserSession: ExistUserSession) : BaseViewModel() {

    val theme: StateFlow<Theme?> = getThemeUseCase().stateIn(viewModelScope,
                                                             SharingStarted.Eagerly,
                                                             null)

    private val _event = Channel<AuthEvent>(Channel.BUFFERED)
    val event = _event.receiveAsFlow()

    fun perform(action: AuthAction) {
        when(action) {
            is ChangeTheme -> changeTheme(action.lightTheme)
            AuthAction.SessionValidation -> validateSession()
        }
    }

    private fun changeTheme(lightTheme: Boolean) {
        viewModelScope.launch {
            setThemeUseCase(lightTheme)
        }
    }

    private fun validateSession() {
        if(existUserSession()) {
            viewModelScope.launch {
                _event.send(AuthEvent.OpenHome)
            }
        }
    }

}
