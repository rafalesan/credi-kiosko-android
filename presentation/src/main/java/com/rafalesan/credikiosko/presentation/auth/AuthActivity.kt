package com.rafalesan.credikiosko.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
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
import com.rafalesan.credikiosko.presentation.main.MainActivity
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
        setupSplash()
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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect {
                    handleEvent(it)
                }
            }
        }
    }

    private fun handleEvent(authEvent: AuthEvent) {
        when(authEvent) {
            AuthEvent.OpenHome -> openHome()
        }
    }

    private fun openHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    private fun setupSplash() {
        val content = findViewById<View>(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(object: ViewTreeObserver.OnPreDrawListener {

            override fun onPreDraw(): Boolean {
                viewModel.perform(AuthAction.SessionValidation)
                content.viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }

        })
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
