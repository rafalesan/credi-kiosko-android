package com.rafalesan.credikiosko.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rafalesan.credikiosko.auth.login.LoginScreenNavCompose
import com.rafalesan.credikiosko.core.commons.presentation.composables.UnderConstruction
import com.rafalesan.credikiosko.core.commons.presentation.theme.CrediKioskoTheme
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
                    startDestination = "login"
                ) {

                    composable("login") {
                        LoginScreenNavCompose(navController)
                    }

                    composable("sign_up") {
                        UnderConstruction()
                    }

                    composable("home") {
                        UnderConstruction()
                    }

                }

            }
        }
    }

}
