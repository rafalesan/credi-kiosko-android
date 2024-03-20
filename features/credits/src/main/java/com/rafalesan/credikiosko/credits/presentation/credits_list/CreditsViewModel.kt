package com.rafalesan.credikiosko.credits.presentation.credits_list

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.credits.domain.entity.Credit
import com.rafalesan.credikiosko.credits.domain.entity.CreditWithCustomerAndProducts
import com.rafalesan.credikiosko.credits.domain.usecase.GetLocalCreditsPagedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreditsViewModel @Inject constructor(
    private val getLocalCreditsPagedUseCase: GetLocalCreditsPagedUseCase
) : BaseViewModel() {

    private val _creditList = MutableStateFlow<PagingData<CreditWithCustomerAndProducts>>(PagingData.empty())
    val creditList = _creditList.asStateFlow()

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
        toast("En construcción (${credit.id})")
    }

    private fun handleCreateNewCreditEvent() {
        toast("En construcción")
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
