package com.rafalesan.credikiosko.auth.login

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.navigation.fragment.findNavController
import com.rafalesan.credikiosko.auth.R
import com.rafalesan.credikiosko.auth.databinding.FrgLoginBinding
import com.rafalesan.credikiosko.auth.mainActivityNameSpace
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModelFragment
import com.rafalesan.credikiosko.presentation.utils.ext.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseViewModelFragment<LoginViewModel, FrgLoginBinding>() {

    override val contentViewLayoutId = R.layout.frg_login
    override val viewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.deviceName = Settings.Global.getString(requireContext().contentResolver,
                                                         "device_name")
    }

    override fun onSubscribeToViewModel() {
        super.onSubscribeToViewModel()
        viewModel.event.collect(this) {
            handleEvent(it)
        }
    }

    private fun handleEvent(loginEvent: LoginEvent) {
        when(loginEvent) {
            LoginEvent.OpenSignup -> openSignup()
            LoginEvent.OpenHome   -> openHome()
        }
    }

    private fun openSignup() {
        findNavController().navigate(R.id.action_to_signup_fragment)
    }

    private fun openHome() {
        val classForName = Class.forName(mainActivityNameSpace)
        val intent = Intent(requireContext(), classForName)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

}
