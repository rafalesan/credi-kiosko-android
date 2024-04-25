package com.rafalesan.credikiosko.credits.presentation.credit_viewer.composables

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rafalesan.credikiosko.core.commons.presentation.composables.CommonDialog
import com.rafalesan.credikiosko.core.commons.presentation.theme.CrediKioskoTheme
import com.rafalesan.credikiosko.credits.R
import com.rafalesan.credikiosko.credits.presentation.credit_viewer.CreditViewerState

@Preview
@Composable
fun PrinterConnectionErrorDialogPreview() {
    CrediKioskoTheme {
        Surface {
            PrinterConnectionErrorDialog(
                viewState = remember {
                    mutableStateOf(
                        CreditViewerState(printerConnectionError = R.string.unable_to_connect_to_printer)
                    )
                }
            )
        }
    }
}

@Composable
fun PrinterConnectionErrorDialog(
    viewState: State<CreditViewerState>,
    onCancelPrintingRetry: () -> Unit = {},
    onRetryPrinting: () -> Unit = {}
) {

    val printerConnectionError by remember {
        derivedStateOf {
            viewState.value.printerConnectionError
        }
    }

    printerConnectionError?.let { connectionErrorStringId ->

        CommonDialog(
            title = stringResource(id = R.string.connection_error),
            descriptionMessage = stringResource(id = connectionErrorStringId),
            negativeButton = {
                TextButton(onClick = onCancelPrintingRetry) {
                    Text(text = stringResource(id = com.rafalesan.credikiosko.core.R.string.cancel))
                }
            },
            positiveButton = {
                TextButton(onClick = onRetryPrinting) {
                    Text(text = stringResource(id = R.string.retry))
                }
            }
        )

    }

}