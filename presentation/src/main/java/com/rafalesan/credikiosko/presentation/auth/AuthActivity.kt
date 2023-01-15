package com.rafalesan.credikiosko.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseActivity
import com.rafalesan.credikiosko.core.commons.presentation.utils.ThemeUtil
import com.rafalesan.credikiosko.presentation.R
import com.rafalesan.credikiosko.presentation.databinding.ActAuthBinding
import com.rafalesan.credikiosko.presentation.extensions.isSystemInDarkTheme
import com.rafalesan.credikiosko.presentation.main.MainActivity
import com.rafalesan.credikiosko.presentation.utils.ext.collect
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
        ThemeUtil.setupIvTheme(this, isSystemInDarkTheme(), binding.ivTheme)
    }

    override fun onSubscribeViewModel() {
        viewModel.theme.collect(this) { theme ->
            ThemeUtil.setTheme(this, theme, binding.ivTheme) { isDarkTheme ->
                viewModel.perform(AuthAction.ChangeTheme(isDarkTheme))
            }
        }

        viewModel.event.collect(this) {
            handleEvent(it)
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

}
