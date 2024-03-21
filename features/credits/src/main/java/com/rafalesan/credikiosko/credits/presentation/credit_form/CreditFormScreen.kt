package com.rafalesan.credikiosko.credits.presentation.credit_form

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rafalesan.credikiosko.core.commons.presentation.composables.ToastHandlerComposable
import com.rafalesan.credikiosko.core.commons.presentation.composables.UnderConstruction

@Composable
fun CreditFormScreen(
    navController: NavHostController,
    viewModel: CreditFormViewModel = hiltViewModel()
) {

    UnderConstruction()
    ToastHandlerComposable(viewModel = viewModel)

}
