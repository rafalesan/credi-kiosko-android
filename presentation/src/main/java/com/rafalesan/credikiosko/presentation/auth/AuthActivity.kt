package com.rafalesan.credikiosko.presentation.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.rafalesan.credikiosko.domain.account.entity.Theme
import com.rafalesan.credikiosko.presentation.R
import com.rafalesan.credikiosko.presentation.base.BaseActivity
import com.rafalesan.credikiosko.presentation.bindingadapters.setImage
import com.rafalesan.credikiosko.presentation.bindingadapters.setTint
import com.rafalesan.credikiosko.presentation.databinding.ActAuthBinding
import com.rafalesan.credikiosko.presentation.extensions.isSystemInDarkTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthActivity : BaseActivity<AuthViewModel, ActAuthBinding>() {

    override val contentLayoutId: Int = R.layout.act_auth
    override val viewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    private fun setup() {
        setupIvTheme(isSystemInDarkTheme())
    }

    override fun onSubscribeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.theme.collect { theme ->
                    setTheme(theme)
                }
            }
        }
    }

    private fun setTheme(theme: Theme?) {

        val isDarkTheme = when(theme) {
            Theme.NIGHT_YES -> false
            Theme.NIGHT_NO -> true
            Theme.NIGHT_UNSPECIFIED -> isSystemInDarkTheme()
            null -> return
        }

        setupIvTheme(isDarkTheme)

        if(isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        binding.ivTheme.setOnClickListener {
            viewModel.perform(AuthAction.ChangeTheme(isDarkTheme))
        }
    }

    private fun setupIvTheme(isDarkTheme: Boolean) {
        if(isDarkTheme) {
            binding.ivTheme.setImage(R.drawable.ic_light_mode)
            binding.ivTheme.setTint(ContextCompat.getColor(this, R.color.yellow))
        } else {
            binding.ivTheme.setImage(R.drawable.ic_dark_mode)
            binding.ivTheme.setTint(ContextCompat.getColor(this, R.color.purple_500))
        }

    }

}
