package com.rafalesan.credikiosko.presentation.main

import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.rafalesan.credikiosko.domain.auth.entity.UserSession
import com.rafalesan.credikiosko.presentation.R
import com.rafalesan.credikiosko.presentation.base.BaseActivity
import com.rafalesan.credikiosko.presentation.databinding.ActMainBinding
import com.rafalesan.credikiosko.presentation.utils.ext.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewModel, ActMainBinding>() {

    override val contentLayoutId: Int = R.layout.act_main
    override val viewModel: MainViewModel by viewModel()

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
