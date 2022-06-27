package com.rafalesan.credikiosko.presentation.home

import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.presentation.R
import com.rafalesan.credikiosko.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.presentation.home.HomeAction.HomeOptionSelected
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class HomeViewModel : BaseViewModel() {

    val homeOptions = getHomeOptions()
            .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    fun perform(action: HomeAction) {
        when(action) {
            is HomeOptionSelected -> handleHomeOptionSelected(action.homeOption)
        }
    }

    private fun handleHomeOptionSelected(homeOption: HomeOption) {
        homeOption.actionIdRes ?: run {
            toast("En construcción")
            return
        }
    }

    private fun getHomeOptions(): Flow<List<HomeOption>> = flow {
        val homeOptions = mutableListOf(HomeOption(R.string.products,
                                                   R.drawable.ic_category),
                                        HomeOption(R.string.customers,
                                                   R.drawable.ic_groups_2),
                                        HomeOption(R.string.credits,
                                                   R.drawable.ic_credit_card),
                                        HomeOption(R.string.cutoffs,
                                                   R.drawable.ic_content_cut))
        emit(homeOptions)
    }



}
