package com.rafalesan.credikiosko.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rafalesan.credikiosko.presentation.base.utils.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {

    protected val _toast = SingleLiveEvent<String>()
    val toast: LiveData<String> = _toast

}