package com.rafalesan.credikiosko.presentation.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rafalesan.credikiosko.presentation.R
import com.rafalesan.credikiosko.presentation.databinding.ActAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    private fun setup() {
        setupBinding()
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.act_auth)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

}
