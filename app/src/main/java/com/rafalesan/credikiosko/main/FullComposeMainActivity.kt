package com.rafalesan.credikiosko.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.rafalesan.credikiosko.auth.AuthViewModel
import com.rafalesan.credikiosko.auth.login.LoginScreenNavCompose
import com.rafalesan.credikiosko.auth.signup.SignUpScreenNavCompose
import com.rafalesan.credikiosko.core.commons.presentation.theme.CrediKioskoTheme
import com.rafalesan.credikiosko.home.HomeScreenNavCompose
import com.rafalesan.products.presentation.products_list.ProductsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullComposeMainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrediKioskoTheme {
                val authViewModel: AuthViewModel = hiltViewModel()
                val navController = rememberNavController()

                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

                val existUserSession by authViewModel.existUserSessionFlow.collectAsState()
                val startDestination by remember {
                    derivedStateOf {
                        if (existUserSession) {
                            "home"
                        } else {
                            "auth"
                        }
                    }
                }
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val drawerGesturesEnabled by remember {
                    derivedStateOf {
                        val currentRoute = navBackStackEntry?.destination?.route
                        if (currentRoute == "login" || currentRoute == "register") {
                            return@derivedStateOf false
                        }
                        true
                    }
                }

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    gesturesEnabled = drawerGesturesEnabled,
                    drawerContent = { DrawerContent() }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = startDestination,
                        enterTransition = { fadeIn(animationSpec = tween(200)) },
                        exitTransition = { fadeOut(animationSpec = tween(200)) },
                        popEnterTransition = { fadeIn(animationSpec = tween(200)) },
                        popExitTransition = { fadeOut(animationSpec = tween(200)) }
                    ) {

                        navigation(
                            route = "auth",
                            startDestination = "login"
                        ) {
                            composable("login") {
                                LoginScreenNavCompose(navController)
                            }

                            composable("sign_up") {
                                SignUpScreenNavCompose()
                            }
                        }


                        composable("home") {
                            HomeScreenNavCompose(navController = navController)
                        }

                        composable("products") {
                            ProductsScreen()
                        }

                    }
                }

            }
        }
    }

}
