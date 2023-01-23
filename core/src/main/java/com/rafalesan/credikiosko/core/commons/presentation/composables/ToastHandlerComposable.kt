package com.rafalesan.credikiosko.core.commons.presentation.composables

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel

@Composable
fun ToastHandlerComposable(viewModel: BaseViewModel) {
    val toastMessage = viewModel.toast.collectAsState(initial = null).value
    toastMessage?.let {
        Toast.makeText(
            LocalContext.current,
            it,
            Toast.LENGTH_LONG
        ).show()
    }
}
