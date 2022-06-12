package com.rafalesan.credikiosko.presentation.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.rafalesan.credikiosko.presentation.BR
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseActivity<VM: BaseViewModel, BINDING: ViewDataBinding> : AppCompatActivity() {

    abstract val contentLayoutId: Int
    abstract val viewModel: VM
    protected lateinit var binding: BINDING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBase()
    }

    private fun setupBase() {
        setupBinding()
        onSubscribeViewModel()
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, contentLayoutId)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
    }

    protected open fun onSubscribeViewModel() {
        viewModel.toast.onEach {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }.launchIn(lifecycleScope)
    }

}
