package com.rafalesan.credikiosko.home.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Groups2
import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.home.R
import com.rafalesan.credikiosko.home.domain.usecase.GetBusinessUseCase
import com.rafalesan.credikiosko.home.presentation.HomeEvent.HomeOptionSelected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBusinessUseCase: GetBusinessUseCase
) : BaseViewModel() {

    private val _viewState = MutableStateFlow(HomeState())
    val viewState = _viewState.asStateFlow()

    private val _action = Channel<HomeAction>(Channel.BUFFERED)
    val action = _action.receiveAsFlow()

    init {
        viewModelScope.launch {
            _viewState.update {
                it.copy(
                    homeOptions = buildHomeOptions(),
                    businessName = getBusinessUseCase().name
                )
            }
        }
    }

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
    private fun buildHomeOptions(): List<HomeOption> {
        return mutableListOf(
            HomeOption(
                R.string.products,
                Icons.Filled.Category,
                "products"
            ),
            HomeOption(
                R.string.customers,
                Icons.Filled.Groups2,
                "customers"
            )
        )
    }

}
