package com.rafalesan.credikiosko.presentation.main

import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.core.commons.domain.entity.Theme
import com.rafalesan.credikiosko.core.commons.domain.usecases.ChangeThemeUseCase
import com.rafalesan.credikiosko.core.commons.domain.usecases.GetThemeUseCase
import com.rafalesan.credikiosko.core.commons.domain.usecases.GetUserSessionInfoUseCase
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.presentation.main.MainAction.ChangeTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getUserSessionInfoUseCase: GetUserSessionInfoUseCase,
    getThemeUseCase: GetThemeUseCase,
    private val changeThemeUseCase: ChangeThemeUseCase
) : BaseViewModel() {

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
