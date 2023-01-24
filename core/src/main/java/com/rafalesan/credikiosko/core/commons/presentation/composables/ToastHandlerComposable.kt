package com.rafalesan.credikiosko.core.commons.presentation.composables

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel

@Composable
fun ToastHandlerComposable(viewModel: BaseViewModel) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.toast.collect { message ->
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
