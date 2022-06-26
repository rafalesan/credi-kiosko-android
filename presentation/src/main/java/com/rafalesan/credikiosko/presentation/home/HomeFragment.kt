package com.rafalesan.credikiosko.presentation.home

import com.rafalesan.credikiosko.presentation.R
import com.rafalesan.credikiosko.presentation.base.BaseViewModelFragment
import com.rafalesan.credikiosko.presentation.databinding.FrgHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseViewModelFragment<HomeViewModel, FrgHomeBinding>() {

    override val contentViewLayoutId = R.layout.frg_home
    override val viewModel: HomeViewModel by viewModel()

}
