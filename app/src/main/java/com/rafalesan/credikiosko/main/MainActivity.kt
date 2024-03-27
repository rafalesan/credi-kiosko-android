package com.rafalesan.credikiosko.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rafalesan.credikiosko.core.commons.emptyString
import com.rafalesan.credikiosko.core.commons.presentation.theme.CrediKioskoTheme
import com.rafalesan.credikiosko.credits.presentation.credit_form.CreditFormScreen
import com.rafalesan.credikiosko.credits.presentation.credit_product_form.CreditProductFormScreen
import com.rafalesan.credikiosko.credits.presentation.credit_viewer.CreditViewerScreen
import com.rafalesan.credikiosko.credits.presentation.credits_list.CreditsScreen
import com.rafalesan.credikiosko.customers.presentation.customer_form.CustomerFormScreen
import com.rafalesan.credikiosko.customers.presentation.customer_selector.CustomerSelectorScreen
import com.rafalesan.credikiosko.customers.presentation.customers_list.CustomersScreen
import com.rafalesan.credikiosko.home.presentation.HomeScreen
import com.rafalesan.credikiosko.onboarding.presentation.WelcomeScreen
import com.rafalesan.credikiosko.products.presentation.product_form.ProductFormScreen
import com.rafalesan.credikiosko.products.presentation.product_selector.ProductSelectorScreen
import com.rafalesan.credikiosko.products.presentation.products_list.ProductsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var keepSplashOnScreen = true
    private val splashScreenDuration = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen().setKeepOnScreenCondition { keepSplashOnScreen }
        Handler(Looper.getMainLooper()).postDelayed({ keepSplashOnScreen = false }, splashScreenDuration)

        super.onCreate(savedInstanceState)

        setContent {
            CrediKioskoTheme {
                val navController = rememberNavController()


                //TODO: BUILD A STRONG TYPED NAVIGATION
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
                        HomeScreen(navController = navController)
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

                    composable("product_selector") {
                        ProductSelectorScreen(navController = navController)
                    }

                    composable("customers") {
                        CustomersScreen(navController = navController)
                    }
                    
                    composable(
                        "customer_form?" +
                            "customer_id={customer_id}?" +
                            "customer_name={customer_name}?" +
                            "customer_nickname={customer_nickname}?" +
                            "customer_email={customer_email}",
                        arguments = listOf(
                            navArgument("customer_id") {
                                nullable = true
                                defaultValue = null
                                type = NavType.StringType
                            },
                            navArgument("customer_name") {
                                defaultValue = emptyString
                                type = NavType.StringType
                            },
                            navArgument("customer_nickname") {
                                defaultValue = emptyString
                                type = NavType.StringType
                            },
                            navArgument("customer_email") {
                                defaultValue = emptyString
                                type = NavType.StringType
                            },
                        )
                    ) {
                        CustomerFormScreen(navController = navController)
                    }

                    composable("customer_selector") {
                        CustomerSelectorScreen(navHostController = navController)
                    }

                    composable("credits") {
                        CreditsScreen(navController = navController)
                    }

                    composable("credit_form") {
                        CreditFormScreen(navController = navController)
                    }

                    composable("credit_product_form") {
                        CreditProductFormScreen(navHostController = navController)
                    }

                    composable("credit_viewer") {
                        CreditViewerScreen(navController = navController)
                    }

                }

            }
        }
    }

}
