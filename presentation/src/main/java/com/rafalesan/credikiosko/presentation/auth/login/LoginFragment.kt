package com.rafalesan.credikiosko.presentation.auth.login

import android.os.Build
import android.os.Bundle
import android.view.View
import com.rafalesan.credikiosko.presentation.R
import com.rafalesan.credikiosko.presentation.base.BaseViewModelFragment
import com.rafalesan.credikiosko.presentation.databinding.FrgLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseViewModelFragment<LoginViewModel, FrgLoginBinding>() {

    override val contentViewLayoutId = R.layout.frg_login
    override val viewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.deviceName = Build.MODEL
    }

}
