package com.rafalesan.credikiosko.presentation.utils.helpers

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.rafalesan.credikiosko.domain.account.entity.Theme
import com.rafalesan.credikiosko.presentation.R
import com.rafalesan.credikiosko.presentation.bindingadapters.setImage
import com.rafalesan.credikiosko.presentation.bindingadapters.setTint
import com.rafalesan.credikiosko.presentation.extensions.isSystemInDarkTheme

object ThemeHelper {

    fun setTheme(context: Context,
                 theme: Theme?,
                 imageViewThemeButton: AppCompatImageView,
                 onImageViewThemeButtonClick: (Boolean) -> Unit) {

        val isDarkTheme = when(theme) {
            Theme.NIGHT_YES         -> false
            Theme.NIGHT_NO          -> true
            Theme.NIGHT_UNSPECIFIED -> context.isSystemInDarkTheme()
            null                    -> return
        }

        setupIvTheme(context, isDarkTheme, imageViewThemeButton)

        if(isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        imageViewThemeButton.setOnClickListener {
            onImageViewThemeButtonClick.invoke(isDarkTheme)
        }
    }

    private fun setupIvTheme(context: Context, isDarkTheme: Boolean, imageViewButton: AppCompatImageView) {
        if(isDarkTheme) {
            imageViewButton.setImage(R.drawable.ic_light_mode)
            imageViewButton.setTint(ContextCompat.getColor(context, R.color.yellow))
        } else {
            imageViewButton.setImage(R.drawable.ic_dark_mode)
            imageViewButton.setTint(ContextCompat.getColor(context, R.color.purple_500))
        }

    }

}
