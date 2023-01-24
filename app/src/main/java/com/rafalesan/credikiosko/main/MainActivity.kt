package com.rafalesan.credikiosko.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.rafalesan.credikiosko.R
import com.rafalesan.credikiosko.core.commons.domain.entity.UserSession
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseActivity
import com.rafalesan.credikiosko.core.commons.presentation.extensions.collect
import com.rafalesan.credikiosko.core.commons.presentation.utils.ThemeUtil
import com.rafalesan.credikiosko.databinding.ActMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActMainBinding>() {

    override val contentLayoutId: Int = R.layout.act_main
    override val viewModel: MainViewModel by viewModels()

    private val appBarConfiguration by lazy { setupAppBarConfiguration() }
    private val navController by lazy { setupNavController() }
    private val navHeaderView by lazy { binding.navigationView.getHeaderView(0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onSubscribeViewModel() {
        super.onSubscribeViewModel()
        viewModel.userSession.collect(this) { userSession ->
            showUserInfoFrom(userSession)
        }
        viewModel.theme.collect(this) {
            viewModel.theme.collect(this) { theme ->
                ThemeUtil.setTheme(this, theme, navHeaderView.findViewById(R.id.ivTheme)) { isDarkTheme ->
                    viewModel.perform(MainAction.ChangeTheme(isDarkTheme))
                }
            }
        }
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

    private fun showUserInfoFrom(userSession: UserSession?) {
        userSession ?: return
        //TODO: THINK THE BEST WAY TO GET BUSINESS NAME AND SHOW IT. IS IT GOOD IDEA TO RETRIEVE BUSINESS NAME IN LOGIN?
        //TODO: THERE IS NO WAY TO HAVE A PROFILE PHOTO IN BACKEND. DO WE NEED THIS? THE APP SHOWS A PROFILE PHOTO PLACEHOLDER
        val tvUserLogged = navHeaderView.findViewById<AppCompatTextView>(R.id.tvUserLogged)
        val tvUserEmail = navHeaderView.findViewById<AppCompatTextView>(R.id.tvUserEmail)
        tvUserLogged.text = userSession.name
        tvUserEmail.text = userSession.email
    }

}
