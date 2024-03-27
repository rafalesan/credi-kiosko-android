package com.rafalesan.credikiosko.credits.presentation.credits_list

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.credits.domain.entity.Credit
import com.rafalesan.credikiosko.credits.domain.entity.CreditWithCustomerAndProducts
import com.rafalesan.credikiosko.credits.domain.usecase.GetLocalCreditsPagedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreditsViewModel @Inject constructor(
    private val getLocalCreditsPagedUseCase: GetLocalCreditsPagedUseCase
) : BaseViewModel() {

    private val _creditList = MutableStateFlow<PagingData<CreditWithCustomerAndProducts>>(PagingData.empty())
    val creditList = _creditList.asStateFlow()

    private val _action = Channel<CreditsAction>(Channel.BUFFERED)
    val action = _action.receiveAsFlow()

    init {
        fetchLocalCredits()
    }

    fun perform(creditsEvent: CreditsEvent) {
        when (creditsEvent) {
            CreditsEvent.CreateNewCredit -> handleCreateNewCreditEvent()
            is CreditsEvent.ShowCredit -> handleShowCreditEvent(creditsEvent.credit)
        }
    }

    private fun handleShowCreditEvent(credit: Credit) {
        viewModelScope.launch {
            _action.send(CreditsAction.ShowCreditViewer(credit))
        }
    }

    private fun handleCreateNewCreditEvent() {
        viewModelScope.launch {
            _action.send(CreditsAction.ShowCreditForm)
        }
    }

    private fun fetchLocalCredits() {
        viewModelScope.launch {
            getLocalCreditsPagedUseCase()
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _creditList.value = it
                }
        }
    }

}
