package com.rafalesan.credikiosko.onboarding.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rafalesan.credikiosko.core.commons.presentation.composables.UnderConstruction

@Composable
fun WelcomeScreen(
    navController: NavHostController,
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    WelcomeUI()
}

@Composable
fun WelcomeUI() {
    UnderConstruction()
}