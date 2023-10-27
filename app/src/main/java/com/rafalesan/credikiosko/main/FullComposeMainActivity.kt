package com.rafalesan.credikiosko.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rafalesan.credikiosko.auth.AuthViewModel
import com.rafalesan.credikiosko.auth.login.LoginScreenNavCompose
import com.rafalesan.credikiosko.auth.signup.SignUpScreenNavCompose
import com.rafalesan.credikiosko.core.commons.presentation.theme.CrediKioskoTheme
import com.rafalesan.credikiosko.home.HomeScreenNavCompose
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
                            "login"
                        }
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    enterTransition = { fadeIn(animationSpec = tween(200)) },
                    exitTransition = { fadeOut(animationSpec = tween(200)) },
                    popEnterTransition = { fadeIn(animationSpec = tween(200)) },
                    popExitTransition = { fadeOut(animationSpec = tween(200)) }
                ) {

                    composable("login") {
                        LoginScreenNavCompose(navController)
                    }

                    composable("sign_up") {
                        SignUpScreenNavCompose()
                    }

                    composable("home") {
                        HomeScreenNavCompose()
                    }

                }

            }
        }
    }

}
