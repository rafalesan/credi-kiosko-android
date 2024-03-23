package com.rafalesan.credikiosko.core.commons.presentation.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController

@Composable
fun <T> NavController.CollectNavigationBackResult(
    key: String,
    initialValue: T,
    resultCallback: (T) -> Unit
) {
    val resultState = currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow(key, initialValue)
        ?.collectAsState()

    LaunchedEffect(resultState) {
        resultState?.value?.let {
            resultCallback(it)
            currentBackStackEntry?.savedStateHandle?.remove<T>(key)
        }
    }
}

fun <T> NavController.popBackStackWithResult(key: String, result: T) {
    previousBackStackEntry
        ?.savedStateHandle
        ?.set(key, result)
    popBackStack()
}