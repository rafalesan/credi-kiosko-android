package com.rafalesan.credikiosko.presentation.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.rafalesan.credikiosko.presentation.R
import com.rafalesan.credikiosko.presentation.base.BaseActivity
import com.rafalesan.credikiosko.presentation.databinding.ActMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewModel, ActMainBinding>() {

    override val contentLayoutId: Int = R.layout.act_main
    override val viewModel: MainViewModel by viewModel()

    private val appBarConfiguration by lazy { setupAppBarConfiguration() }
    private val navController by lazy { setupNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    private fun setup() {
        setupToolbar()
        setupDrawer()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.includeContent.findViewById(R.id.toolbar))
    }

    private fun setupDrawer() {
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setupAppBarConfiguration(): AppBarConfiguration {
        return AppBarConfiguration.Builder(R.id.homeFragment)
                .setOpenableLayout(binding.drawerLayout)
                .build()
    }

    private fun setupNavController(): NavController {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainNavHost) as NavHostFragment
        return navHostFragment.navController
    }

}
