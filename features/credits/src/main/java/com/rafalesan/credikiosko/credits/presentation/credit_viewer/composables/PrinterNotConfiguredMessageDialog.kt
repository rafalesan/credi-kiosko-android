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
fun PrinterNotConfiguredMessageDialogPreview() {

    CrediKioskoTheme {
        Surface {
            PrinterNotConfiguredMessageDialog(
                viewState = remember {
                    mutableStateOf(
                        CreditViewerState(isShowingPrinterNotConfiguredMessage = true)
                    )
                }
            )
        }
    }

}

@Composable
fun PrinterNotConfiguredMessageDialog(
    viewState: State<CreditViewerState>,
    onCancelPrinterConfiguration: () -> Unit = {},
    onStartPrinterConfiguration: () -> Unit = {}
) {

    val isShowingPrinterNotConfiguredMessage by remember {
        derivedStateOf {
            viewState.value.isShowingPrinterNotConfiguredMessage
        }
    }

    if (isShowingPrinterNotConfiguredMessage) {

        CommonDialog(
            title = stringResource(id = R.string.printer_not_configured),
            descriptionMessage = stringResource(id = R.string.printer_not_configured_desc),
            negativeButton = {
                TextButton(onClick = onCancelPrinterConfiguration) {
                    Text(text = stringResource(id = com.rafalesan.credikiosko.core.R.string.cancel))
                }
            },
            positiveButton = {
                TextButton(onClick = onStartPrinterConfiguration) {
                    Text(text = stringResource(id = R.string.configure))
                }
            }
        )

    }

}