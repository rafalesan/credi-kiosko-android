package com.rafalesan.credikiosko.home

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes

data class HomeOption(@StringRes val optionNameResId: Int,
                      @DrawableRes val optionIconResId: Int,
                      @IdRes val actionIdRes: Int? = null)
