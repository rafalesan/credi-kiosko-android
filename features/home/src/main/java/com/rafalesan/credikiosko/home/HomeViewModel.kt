package com.rafalesan.credikiosko.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Groups2
import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.core.commons.domain.usecases.GetUserSessionInfoUseCase
import com.rafalesan.credikiosko.core.commons.emptyUserSession
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.home.HomeEvent.HomeOptionSelected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getUserSessionInfoUseCase: GetUserSessionInfoUseCase
) : BaseViewModel() {

    val homeOptions = MutableStateFlow(getHomeOptions2())
    val userSessionInfo = getUserSessionInfoUseCase()
        .filterNotNull()
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyUserSession
        )

    private val _action = Channel<HomeAction>(Channel.BUFFERED)
    val action = _action.receiveAsFlow()

    fun perform(action: HomeEvent) {
        when(action) {
            is HomeOptionSelected -> handleHomeOptionSelected(action.homeOption)
        }
    }

    private fun handleHomeOptionSelected(homeOption: HomeOption) {
        homeOption.destination ?: run {
            toast("En construcción")
            return
        }
        viewModelScope.launch {
            _action.send(
                HomeAction.NavigateTo(homeOption.destination)
            )
        }
    }
    private fun getHomeOptions2(): List<HomeOption> {
        return mutableListOf(
            HomeOption(
                R.string.products,
                Icons.Filled.Category,
                "products"
            ),
            HomeOption(
                R.string.customers,
                Icons.Filled.Groups2
            ),
            HomeOption(
                R.string.credits,
                Icons.Filled.CreditCard
            ),
            HomeOption(
                R.string.cutoffs,
                Icons.Filled.ContentCut
            )
        )
    }

}
