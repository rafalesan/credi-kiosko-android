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
fun PrinterThatConnectionFailedDialogPreview() {
    CrediKioskoTheme {
        Surface {
            PrinterThatConnectionFailedDialog(
                viewState = remember {
                    mutableStateOf(
                        CreditViewerState(printerThatConnectionFailed = "Bluetooth Printer")
                    )
                }
            )
        }
    }
}

@Composable
fun PrinterThatConnectionFailedDialog(
    viewState: State<CreditViewerState>,
    onCancelPrintingRetry: () -> Unit = {},
    onStartPrinterConfiguration: () -> Unit = {},
    onRetryPrinting: () -> Unit = {}
) {

    val printerThatConnectionFailed by remember {
        derivedStateOf {
            viewState.value.printerThatConnectionFailed
        }
    }

    printerThatConnectionFailed?.let { printerConnectionFailed ->

        val message = stringResource(
            id = R.string.unable_to_connect_to_printer_x,
            printerConnectionFailed
        )

        CommonDialog(
            title = stringResource(id = R.string.connection_error),
            descriptionMessage = message,
            negativeButton = {
                TextButton(onClick = onCancelPrintingRetry) {
                    Text(text = stringResource(id = com.rafalesan.credikiosko.core.R.string.cancel))
                }
            },
            neutralButton = {
                TextButton(onClick = onStartPrinterConfiguration) {
                    Text(text = stringResource(id = R.string.configure_new_printer))
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
