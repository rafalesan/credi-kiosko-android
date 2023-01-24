package com.rafalesan.credikiosko.home

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class HomeOption(@StringRes val optionNameResId: Int,
                      val iconVector: ImageVector,
                      @IdRes val actionIdRes: Int? = null)
