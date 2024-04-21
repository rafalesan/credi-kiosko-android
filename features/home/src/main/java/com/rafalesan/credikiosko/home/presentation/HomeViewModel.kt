package com.rafalesan.credikiosko.home.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Groups2
import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.core.commons.data.build_config_provider.BuildConfigFieldsProvider
import com.rafalesan.credikiosko.core.commons.domain.exception.ValidationsException
import com.rafalesan.credikiosko.core.commons.domain.utils.Result
import com.rafalesan.credikiosko.core.commons.domain.validator.BusinessInputValidator
import com.rafalesan.credikiosko.core.commons.domain.validator.BusinessInputValidator.BusinessInputValidation.EMPTY_BUSINESS_NAME
import com.rafalesan.credikiosko.core.commons.emptyString
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.home.R
import com.rafalesan.credikiosko.home.domain.usecase.GetBusinessUseCase
import com.rafalesan.credikiosko.home.domain.usecase.UpdateBusinessNameUseCase
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
    private val buildConfigFieldsProvider: BuildConfigFieldsProvider,
    private val getBusinessUseCase: GetBusinessUseCase,
    private val updateBusinessNameUseCase: UpdateBusinessNameUseCase
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
                    businessName = getBusinessUseCase().name,
                    appVersion = buildAppVersion()
                )
            }
        }
    }

    fun perform(action: HomeEvent) {
        when(action) {
            is HomeOptionSelected -> handleHomeOptionSelected(action.homeOption)
            is HomeEvent.BusinessNameInputChanged -> handleBusinessNameInputChanged(action.businessName)
            HomeEvent.EditBusinessName -> handleEditBusinessName()
            HomeEvent.SubmitBusinessName -> handleSubmitBusinessName()
            HomeEvent.CancelBusinessNameEdit -> handleCancelBusinessNameEdit()
        }
    }

    private fun handleCancelBusinessNameEdit() {
        _viewState.update {
            it.copy(
                isShowingEditBusinessNameDialog = false,
                inputBusinessName = emptyString
            )
        }
    }

    private fun handleSubmitBusinessName() {
        viewModelScope.launch {
            val result = updateBusinessNameUseCase(viewState.value.inputBusinessName)

            when (result) {
                is Result.Success -> _viewState.update {
                    it.copy(
                        isShowingEditBusinessNameDialog = false,
                        businessName = getBusinessUseCase().name,
                        inputBusinessName = emptyString
                    )
                }
                is Result.Error -> handleSubmitBusinessNameErrors(result.exception)
            }

        }
    }

    private fun handleSubmitBusinessNameErrors(exception: Exception) {
        val validationException = exception as? ValidationsException ?: return
        validationException.validations.forEach {
            val validation = it as BusinessInputValidator.BusinessInputValidation
            when (validation) {
                EMPTY_BUSINESS_NAME -> _viewState.update { state ->
                    state.copy(inputBusinessNameError = validation.errorResId)
                }
            }
        }
    }

    private fun handleEditBusinessName() {
        _viewState.update {
            it.copy(
                isShowingEditBusinessNameDialog = true
            )
        }
    }

    private fun handleBusinessNameInputChanged(businessName: String) {
        _viewState.update {
            it.copy(
                inputBusinessName = businessName,
                inputBusinessNameError = null
            )
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
            ),
            HomeOption(
                R.string.credits,
                Icons.Filled.CreditCard,
                "credits"
            )
        )
    }

    private fun buildAppVersion(): String {
        val versionName = buildConfigFieldsProvider.get().versionName
        val versionCode = buildConfigFieldsProvider.get().versionCode

        return "$versionName ($versionCode)"
    }

}
