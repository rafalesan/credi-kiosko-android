package com.rafalesan.credikiosko.presentation.main

import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.domain.account.entity.Theme
import com.rafalesan.credikiosko.domain.account.usecases.ChangeThemeUseCase
import com.rafalesan.credikiosko.domain.account.usecases.GetThemeUseCase
import com.rafalesan.credikiosko.domain.account.usecases.GetUserSessionInfoUseCase
import com.rafalesan.credikiosko.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.presentation.main.MainAction.ChangeTheme
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(getUserSessionInfoUseCase: GetUserSessionInfoUseCase,
                    getThemeUseCase: GetThemeUseCase,
                    private val changeThemeUseCase: ChangeThemeUseCase) : BaseViewModel() {

    val userSession = getUserSessionInfoUseCase()
            .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val theme: StateFlow<Theme?> = getThemeUseCase().stateIn(viewModelScope,
                                                             SharingStarted.Eagerly,
                                                             null)

    fun perform(action: MainAction) {
        when(action) {
            is ChangeTheme -> changeTheme(action.lightTheme)
        }
    }

    private fun changeTheme(lightTheme: Boolean) {
        viewModelScope.launch {
            changeThemeUseCase(lightTheme)
        }
    }

}
