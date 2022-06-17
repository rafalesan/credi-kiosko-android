package com.rafalesan.credikiosko.presentation.main

import com.rafalesan.credikiosko.presentation.R
import com.rafalesan.credikiosko.presentation.base.BaseActivity
import com.rafalesan.credikiosko.presentation.databinding.ActMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewModel, ActMainBinding>() {

    override val contentLayoutId: Int = R.layout.act_main
    override val viewModel: MainViewModel by viewModel()

}
