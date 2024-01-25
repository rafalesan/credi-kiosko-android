package com.rafalesan.credikiosko.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rafalesan.credikiosko.auth.AuthViewModel
import com.rafalesan.credikiosko.auth.login.LoginScreenNavCompose
import com.rafalesan.credikiosko.auth.signup.SignUpScreenNavCompose
import com.rafalesan.credikiosko.core.commons.emptyString
import com.rafalesan.credikiosko.core.commons.presentation.theme.CrediKioskoTheme
import com.rafalesan.credikiosko.home.HomeScreenNavCompose
import com.rafalesan.products.presentation.product_form.ProductFormScreen
import com.rafalesan.products.presentation.products_list.ProductsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullComposeMainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrediKioskoTheme {
                val authViewModel: AuthViewModel = hiltViewModel()
                val navController = rememberNavController()

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

                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    enterTransition = { fadeIn(animationSpec = tween()) },
                    exitTransition = { ExitTransition.None },
                    popEnterTransition = { EnterTransition.None },
                    popExitTransition = { fadeOut(animationSpec = tween()) }
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
                        ProductsScreen(navController = navController)
                    }

                    composable(
                        "product_form?product_id={product_id}?product_name={product_name}?product_price={product_price}",
                        arguments = listOf(
                            navArgument("product_id") {
                                nullable = true
                                defaultValue = null
                                type = NavType.StringType
                            },
                            navArgument("product_name") {
                                defaultValue = emptyString
                                type = NavType.StringType
                            },
                            navArgument("product_price") {
                                defaultValue = emptyString
                                type = NavType.StringType
                            },
                        )
                    ) {
                        ProductFormScreen(navController = navController)
                    }

                }

            }
        }
    }

}
