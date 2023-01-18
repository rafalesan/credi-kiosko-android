package com.rafalesan.credikiosko.auth.signup

import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.fragment.app.viewModels
import com.rafalesan.credikiosko.auth.R
import com.rafalesan.credikiosko.auth.databinding.FrgSignupBinding
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : BaseViewModelFragment<SignupViewModel, FrgSignupBinding>() {

    override val contentViewLayoutId = R.layout.frg_signup
    override val viewModel: SignupViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.deviceName = Settings.Global.getString(requireContext().contentResolver,
                                                         "device_name")
    }

}
