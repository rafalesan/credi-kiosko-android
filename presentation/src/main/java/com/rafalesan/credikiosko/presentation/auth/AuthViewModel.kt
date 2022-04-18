package com.rafalesan.credikiosko.presentation.auth

import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.domain.account.entity.Theme
import com.rafalesan.credikiosko.domain.account.usecases.ChangeThemeUseCase
import com.rafalesan.credikiosko.domain.account.usecases.GetThemeUseCase
import com.rafalesan.credikiosko.presentation.auth.AuthAction.ChangeTheme
import com.rafalesan.credikiosko.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AuthViewModel(getThemeUseCase: GetThemeUseCase,
                    private val setThemeUseCase: ChangeThemeUseCase) : BaseViewModel() {

    val theme: StateFlow<Theme?> = getThemeUseCase().stateIn(viewModelScope,
                                                             SharingStarted.Eagerly,
                                                             null)

    fun perform(action: AuthAction) {
        when(action) {
            is ChangeTheme -> changeTheme(action.lightTheme)
        }
    }

    private fun changeTheme(lightTheme: Boolean) {
        viewModelScope.launch {
            setThemeUseCase(lightTheme)
        }
    }

}
