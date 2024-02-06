package com.rafalesan.credikiosko.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rafalesan.credikiosko.core.commons.emptyString
import com.rafalesan.credikiosko.core.commons.presentation.theme.CrediKioskoTheme
import com.rafalesan.credikiosko.home.presentation.HomeScreenNavCompose
import com.rafalesan.credikiosko.onboarding.presentation.WelcomeScreen
import com.rafalesan.products.presentation.product_form.ProductFormScreen
import com.rafalesan.products.presentation.products_list.ProductsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FullComposeMainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrediKioskoTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "welcome",
                    enterTransition = { fadeIn(animationSpec = tween()) },
                    exitTransition = { ExitTransition.None },
                    popEnterTransition = { EnterTransition.None },
                    popExitTransition = { fadeOut(animationSpec = tween()) }
                ) {

                    composable("welcome") {
                        WelcomeScreen(navController)
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
