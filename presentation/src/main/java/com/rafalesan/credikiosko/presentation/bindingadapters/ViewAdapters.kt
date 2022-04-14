package com.rafalesan.credikiosko.presentation.bindingadapters

import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("app:errorText")
fun TextInputLayout.setErrorText(@StringRes stringResId: Int?) {
    stringResId ?: return run {
        this.error = null
    }
    this.error = context.getString(stringResId)
}
