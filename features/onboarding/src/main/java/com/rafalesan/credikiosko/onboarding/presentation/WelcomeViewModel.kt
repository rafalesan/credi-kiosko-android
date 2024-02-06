package com.rafalesan.credikiosko.onboarding.presentation

import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.core.commons.domain.usecases.ExistBusinessUseCase
import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.onboarding.domain.usecase.SaveBusinessUseCase
import com.rafalesan.credikiosko.onboarding.domain.validator.BusinessInputValidator
import com.rafalesan.credikiosko.onboarding.domain.validator.BusinessInputValidator.BusinessInputValidation.EMPTY_BUSINESS_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val saveBusinessUseCase: SaveBusinessUseCase,
    private val existBusinessUseCase: ExistBusinessUseCase
): BaseViewModel() {

    private val _viewState = MutableStateFlow(WelcomeState())
    val viewState = _viewState.asStateFlow()

    private val _action = Channel<WelcomeAction>(Channel.BUFFERED)
    val action = _action.receiveAsFlow()

    fun perform(event: WelcomeEvent) {
        when (event) {
            is WelcomeEvent.SetBusinessName -> _viewState.update {
                it.copy(businessName = event.businessName)
            }
            WelcomeEvent.ConfirmBusinessName -> confirmBusinessName()
            WelcomeEvent.CheckIfBusinessExists -> checkIfBusinessExists()
        }
    }

    private fun checkIfBusinessExists() {
        viewModelScope.launch {

            if (existBusinessUseCase()) {
                _action.send(WelcomeAction.OpenHome)
            }

        }
    }

    private fun confirmBusinessName() {
        clearFormErrors()

        viewModelScope.launch {
            val result = saveBusinessUseCase.invoke(viewState.value.businessName)

            when (result) {
                is ResultOf.Success -> {
                    _action.send(WelcomeAction.OpenHome)
                }
                is ResultOf.InvalidData -> {
                    handleInvalidDataResult(result)
                }
                else -> {
                    Timber.e("Operation not supported: $result")
                }
            }
        }

    }

    private fun handleInvalidDataResult(
        result: ResultOf.InvalidData<BusinessInputValidator.BusinessInputValidation>
    ) {
        result.validations.forEach { validation ->
            when (validation) {
                EMPTY_BUSINESS_NAME -> _viewState.update {
                    it.copy(businessInputError = validation.errorResId)
                }
            }
        }
    }

    private fun clearFormErrors() {
        _viewState.update {
            it.copy(businessInputError = null)
        }
    }

}
