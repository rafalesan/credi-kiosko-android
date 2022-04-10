package com.rafalesan.credikiosko.presentation.auth.login

import androidx.fragment.app.viewModels
import com.rafalesan.credikiosko.presentation.R
import com.rafalesan.credikiosko.presentation.base.BaseViewModelFragment
import com.rafalesan.credikiosko.presentation.databinding.FrgLoginBinding

class LoginFragment : BaseViewModelFragment<LoginViewModel, FrgLoginBinding>() {

    override val contentViewLayoutId = R.layout.frg_login
    override val viewModel: LoginViewModel by viewModels()

}
