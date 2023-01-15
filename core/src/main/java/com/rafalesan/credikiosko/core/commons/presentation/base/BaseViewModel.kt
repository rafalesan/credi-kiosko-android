package com.rafalesan.credikiosko.core.commons.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafalesan.credikiosko.core.commons.presentation.utils.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _toast = Channel<String>(Channel.BUFFERED)
    val toast = _toast.receiveAsFlow()

    protected val _uiState = Channel<UiState>(Channel.BUFFERED)
    val uiState = _uiState.receiveAsFlow()

    protected fun toast(message: String?) {
        viewModelScope.launch {
            _toast.send(message ?: "")
        }
    }

}