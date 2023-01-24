package com.rafalesan.credikiosko.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Groups2
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.home.HomeAction.HomeOptionSelected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {

    val homeOptions = MutableStateFlow(getHomeOptions2())

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
    private fun getHomeOptions2(): List<HomeOption> {
        return mutableListOf(
            HomeOption(
                R.string.products,
                Icons.Filled.Category
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
