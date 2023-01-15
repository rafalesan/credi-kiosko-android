package com.rafalesan.credikiosko.presentation.auth.signup

import android.os.Bundle
import android.provider.Settings
import android.view.View
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModelFragment
import com.rafalesan.credikiosko.presentation.R
import com.rafalesan.credikiosko.presentation.databinding.FrgSignupBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupFragment : BaseViewModelFragment<SignupViewModel, FrgSignupBinding>() {

    override val contentViewLayoutId = R.layout.frg_signup
    override val viewModel: SignupViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.deviceName = Settings.Global.getString(requireContext().contentResolver,
                                                         "device_name")
    }

}
