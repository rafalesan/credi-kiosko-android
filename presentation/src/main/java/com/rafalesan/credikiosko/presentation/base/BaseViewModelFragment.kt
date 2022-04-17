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
import androidx.lifecycle.lifecycleScope
import com.rafalesan.credikiosko.presentation.BR
import com.rafalesan.credikiosko.presentation.R
import com.rafalesan.credikiosko.presentation.base.utils.AutoClearedValue
import com.rafalesan.credikiosko.presentation.base.utils.DialogHelper
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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
