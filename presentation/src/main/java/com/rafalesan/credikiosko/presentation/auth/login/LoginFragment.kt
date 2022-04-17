package com.rafalesan.credikiosko.presentation.auth.login

import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.rafalesan.credikiosko.presentation.R
import com.rafalesan.credikiosko.presentation.base.BaseViewModelFragment
import com.rafalesan.credikiosko.presentation.base.utils.DialogHelper
import com.rafalesan.credikiosko.presentation.databinding.FrgLoginBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    handleUiState(it)
                }

            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect {
                    handleEvent(it)
                }
            }
        }
    }

    private fun handleUiState(uiState: LoginUiState) {
        when(uiState) {
            is LoginUiState.ApiError     -> DialogHelper.showApiErrorDialog(requireContext(), uiState.message)
            LoginUiState.ApiNotAvailable -> DialogHelper.showApiNotAvailableErrorDialog(requireContext())
            is LoginUiState.Loading      -> showProgress(isLoading = uiState.isLoading, uiState.stringResMessageId)
            LoginUiState.NoInternet      -> DialogHelper.showNoInternetDialog(requireContext())
            LoginUiState.UnknownError    -> DialogHelper.showUnknownErrorDialog(requireContext())
            LoginUiState.Idle            -> { Timber.d("Login is idle") }
        }
    }

    private fun handleEvent(loginEvent: LoginEvent) {
        when(loginEvent) {
            LoginEvent.OpenSignup -> openSignup()
        }
    }

    private fun openSignup() {
        findNavController().navigate(R.id.action_to_signup_fragment)
    }

}
