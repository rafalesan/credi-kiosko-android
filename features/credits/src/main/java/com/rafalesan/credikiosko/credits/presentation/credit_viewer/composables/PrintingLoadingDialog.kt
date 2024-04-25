package com.rafalesan.credikiosko.credits.presentation.credit_viewer.composables

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rafalesan.credikiosko.core.commons.presentation.composables.LoadingDialog
import com.rafalesan.credikiosko.core.commons.presentation.theme.CrediKioskoTheme
import com.rafalesan.credikiosko.credits.R
import com.rafalesan.credikiosko.credits.presentation.credit_viewer.CreditViewerState

@Preview
@Composable
fun PrintingLoadingDialogPreview() {
    CrediKioskoTheme {
        Surface {
            PrintingLoadingDialog(
                viewState = remember {
                    mutableStateOf(CreditViewerState(printLoadingStringResId = R.string.printing))
                }
            )
        }
    }
}

@Composable
fun PrintingLoadingDialog(
    viewState: State<CreditViewerState>
) {

    val printLoadingTextId by remember {
        derivedStateOf {
            viewState.value.printLoadingStringResId
        }
    }

    printLoadingTextId?.let {
        LoadingDialog(loadingText = stringResource(id = it))
    }

}