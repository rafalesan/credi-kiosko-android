package com.rafalesan.credikiosko.credits.presentation.credit_viewer.composables

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.rafalesan.credikiosko.core.commons.presentation.composables.CommonDialog
import com.rafalesan.credikiosko.credits.R
import com.rafalesan.credikiosko.credits.presentation.credit_viewer.CreditViewerState

@Composable
fun BluetoothPermissionDeniedMessageDialog(
    viewState: State<CreditViewerState>,
    onCancelBluetoothPermissionRequestFromSettings: () -> Unit,
    onRequestBluetoothPermissionFromSettings: () -> Unit
) {
    val isShowingBluetoothPermissionDeniedMessage by remember {
        derivedStateOf {
            viewState.value.isShowingBluetoothPermissionDeniedMessage
        }
    }

    if (isShowingBluetoothPermissionDeniedMessage) {
        CommonDialog(
            title = stringResource(id = R.string.bluetooth_permission_denied),
            descriptionMessage = stringResource(id = R.string.bluetooth_permission_denied_desc),
            negativeButton = {
                TextButton(onClick = onCancelBluetoothPermissionRequestFromSettings) {
                    Text(text = stringResource(id = com.rafalesan.credikiosko.core.R.string.cancel))
                }
            },
            positiveButton = {
                TextButton(onClick = onRequestBluetoothPermissionFromSettings) {
                    Text(text = stringResource(id = R.string.go_to_settings))
                }
            }
        )
    }
}