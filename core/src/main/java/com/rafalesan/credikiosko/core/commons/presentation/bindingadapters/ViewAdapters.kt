package com.rafalesan.credikiosko.presentation.bindingadapters

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("app:errorText")
fun TextInputLayout.setErrorText(@StringRes stringResId: Int?) {
    stringResId ?: return run {
        this.error = null
    }
    this.error = context.getString(stringResId)
}

@BindingAdapter("app:errorTextString")
fun TextInputLayout.setErrorTextString(stringText: String?) {
    stringText ?: return run {
        this.error = null
    }
    this.error = stringText
}

@BindingAdapter("app:tint")
fun AppCompatImageView.setTint(colorInt: Int) {
    this.setColorFilter(colorInt, PorterDuff.Mode.SRC_IN)
}

@BindingAdapter("android:src")
fun AppCompatImageView.setImage(resourceId: Int) {
    val drawable: Drawable? = VectorDrawableCompat.create(this.resources, resourceId, this.context.theme)
    this.setImageDrawable(drawable)
}
