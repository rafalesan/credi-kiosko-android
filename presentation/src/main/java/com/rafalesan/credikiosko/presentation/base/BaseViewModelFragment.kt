package com.rafalesan.credikiosko.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.rafalesan.credikiosko.presentation.BR
import com.rafalesan.credikiosko.presentation.R
import com.rafalesan.credikiosko.presentation.base.utils.AutoClearedValue
import com.rafalesan.credikiosko.presentation.base.utils.DialogHelper
import com.rafalesan.credikiosko.presentation.base.utils.UiState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModelFragment<VM: BaseViewModel, VB: ViewDataBinding> : Fragment() {

    abstract val contentViewLayoutId: Int
    abstract val viewModel: VM

    protected var binding by AutoClearedValue<VB>()
    protected var progressDialog by AutoClearedValue<AlertDialog>()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = createBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBase()
    }

    private fun createBinding(inflater: LayoutInflater,
                              container: ViewGroup?) : VB {
        return DataBindingUtil.inflate(inflater,
                                       contentViewLayoutId,
                                       container,
                                       false)
    }

    private fun setupBase() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(BR.viewModel, viewModel)
        progressDialog = DialogHelper.buildProgressDialog(requireContext())
        onSubscribeToViewModel()
    }

    protected open fun onSubscribeToViewModel() {
        viewModel.toast.onEach {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    handleUiState(it)
                }

            }
        }
    }

    private fun handleUiState(uiState: UiState) {
        when(uiState) {
            is UiState.ApiError     -> DialogHelper.showApiErrorDialog(requireContext(), uiState.message)
            UiState.ApiNotAvailable -> DialogHelper.showApiNotAvailableErrorDialog(requireContext())
            is UiState.Loading      -> showProgress(isLoading = uiState.isLoading, uiState.stringResMessageId)
            UiState.NoInternet      -> DialogHelper.showNoInternetDialog(requireContext())
            UiState.UnknownError    -> DialogHelper.showUnknownErrorDialog(requireContext())
            UiState.Idle            -> { Timber.d("Login is idle") }
        }
    }

    protected fun showProgress(isLoading: Boolean, description: Int? = null) {
        if(isLoading) {
            progressDialog.show()
        } else {
            progressDialog.hide()
        }
        val descriptionString = getString(description ?: R.string.loading)
        val tvDescription = progressDialog.findViewById<AppCompatTextView>(R.id.tvDescription)
        tvDescription?.text = descriptionString

    }

}
