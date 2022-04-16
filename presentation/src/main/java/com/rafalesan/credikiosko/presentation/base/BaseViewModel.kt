package com.rafalesan.credikiosko.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected val _toast = Channel<String>(Channel.BUFFERED)
    val toast = _toast.receiveAsFlow()

    protected fun toast(message: String?) {
        viewModelScope.launch {
            _toast.send(message ?: "")
        }
    }

}