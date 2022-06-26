package com.rafalesan.credikiosko.presentation.main

import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.domain.account.usecases.GetUserSessionInfoUseCase
import com.rafalesan.credikiosko.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class MainViewModel(getUserSessionInfoUseCase: GetUserSessionInfoUseCase) : BaseViewModel() {

    val userSession = getUserSessionInfoUseCase()
            .stateIn(viewModelScope, SharingStarted.Eagerly, null)

}
