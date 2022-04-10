package com.rafalesan.credikiosko.presentation.base

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.rafalesan.credikiosko.presentation.BR
import com.rafalesan.credikiosko.presentation.base.utils.AutoClearedValue

abstract class BaseViewModelFragment<VM: BaseViewModel, VB: ViewDataBinding> : Fragment() {

    abstract val contentViewLayoutId: Int
    abstract val viewModel: VM

    protected var binding by AutoClearedValue<VB>()

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
        onSubscribeToViewModel()
    }

    protected open fun onSubscribeToViewModel() {
        viewModel.toast.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

}
