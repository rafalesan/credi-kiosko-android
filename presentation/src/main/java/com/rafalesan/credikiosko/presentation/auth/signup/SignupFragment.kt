package com.rafalesan.credikiosko.presentation.auth.signup

import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.rafalesan.credikiosko.presentation.R
import com.rafalesan.credikiosko.presentation.base.BaseViewModelFragment
import com.rafalesan.credikiosko.presentation.base.utils.DialogHelper
import com.rafalesan.credikiosko.presentation.databinding.FrgSignupBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SignupFragment : BaseViewModelFragment<SignupViewModel, FrgSignupBinding>() {

    override val contentViewLayoutId = R.layout.frg_signup
    override val viewModel: SignupViewModel by viewModel()

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
    }

    private fun handleUiState(uiState: SignupUiState) {
        when(uiState) {
            is SignupUiState.Loading     -> showProgress(isLoading = uiState.isLoading, uiState.stringResMessageId)
            is SignupUiState.ApiError    -> DialogHelper.showApiErrorDialog(requireContext(), uiState.message)
            SignupUiState.ApiNotAvailable -> DialogHelper.showApiNotAvailableErrorDialog(requireContext())
            SignupUiState.NoInternet   -> DialogHelper.showNoInternetDialog(requireContext())
            SignupUiState.UnknownError -> DialogHelper.showUnknownErrorDialog(requireContext())
            SignupUiState.Idle         -> { Timber.d("Signup is idle") }
        }
    }

}
