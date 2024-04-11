@file:OptIn(ExperimentalMaterial3Api::class)

package com.rafalesan.credikiosko.core.bluetooth_printer.presentation

import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.rafalesan.credikiosko.core.R
import com.rafalesan.credikiosko.core.bluetooth_printer.presentation.model.BluetoothDeviceInfo
import com.rafalesan.credikiosko.core.commons.presentation.theme.CrediKioskoTheme
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens
import timber.log.Timber

@SuppressLint("MissingPermission")
@Composable
fun BluetoothPrinterConfigBottomSheet(
    onDismissCallback: (Boolean) -> Unit
) {

    val viewModel: BluetoothPrinterConfigViewModel = hiltViewModel()

    val context = LocalContext.current
    val bluetoothManager = ContextCompat.getSystemService(context, BluetoothManager::class.java)
    val bluetoothAdapter = bluetoothManager?.adapter ?: run {
        Timber.e("This android devices does not support bluetooth")
        null
    }

    bluetoothAdapter?.let {
        viewModel.perform(
            BluetoothPrinterConfigEvent.SetBondedBluetoothDevices(it.bondedDevices.toList())
        )
    }

    ModalBottomSheet(
        onDismissRequest = {
            onDismissCallback(false)
        }
    ) {
        BluetoothPrinterConfigUI(
            viewState = viewModel.viewState.collectAsState(),
            onDismissCallback = onDismissCallback,
            onBluetoothDevicePressed = {
                viewModel.perform(BluetoothPrinterConfigEvent.BluetoothDevicePressed(it))
            }
        )
    }

}

@Preview
@Composable
fun BluetoothPrinterConfigUIPreview() {
    CrediKioskoTheme {
        Surface {
            BluetoothPrinterConfigUI(
                viewState = remember {
                    mutableStateOf(
                        MOCKED_BLUETOOTH_DEVICES_FOR_PREVIEW
                    )
                }
            )
        }
    }

}

@Composable
fun BluetoothPrinterConfigUI(
    viewState: State<BluetoothPrinterConfigViewState>,
    onDismissCallback: (Boolean) -> Unit = {},
    onBluetoothDevicePressed: (BluetoothDeviceInfo) -> Unit = {}
) {


    val isPrinterConfigured by remember {
        derivedStateOf {
            viewState.value.isPrinterConfigured
        }
    }

    if (isPrinterConfigured) {
        onDismissCallback(true)
    }

    val bondedBluetoothDevices by remember {
        derivedStateOf {
            viewState.value.bondedBluetoothDevices
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Text(
            modifier = Modifier
                .padding(horizontal = Dimens.space2x)
                .padding(bottom = Dimens.spaceDefault),
            text = stringResource(id = R.string.select_the_printer),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = Dimens.space4x)
        ) {
            items(bondedBluetoothDevices) {
                BluetoothDeviceItem(
                    bluetoothDevice = it,
                    onBluetoothDevicePressed = onBluetoothDevicePressed
                )
                Divider(color = Color.LightGray, thickness = Dimens.dividerThickness)
            }
        }

    }

}


@SuppressLint("MissingPermission")
@Composable
fun BluetoothDeviceItem(
    bluetoothDevice: BluetoothDeviceInfo,
    onBluetoothDevicePressed: (BluetoothDeviceInfo) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onBluetoothDevicePressed(bluetoothDevice)
            }
            .padding(horizontal = Dimens.space2x)
    ) {
        Text(
            modifier = Modifier
                .padding(top = Dimens.spaceDefault),
            text = bluetoothDevice.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .padding(bottom = Dimens.spaceDefault),
            text = bluetoothDevice.macAddress,
        )
    }
}

private val MOCKED_BLUETOOTH_DEVICES_FOR_PREVIEW = BluetoothPrinterConfigViewState(
    bondedBluetoothDevices = listOf(
        BluetoothDeviceInfo(
            name = "Device 1",
            macAddress = "00:00:00:00:00:00"
        ),
        BluetoothDeviceInfo(
            name = "Device 2",
            macAddress = "00:00:00:00:00:01"
        ),
        BluetoothDeviceInfo(
            name = "Device 3",
            macAddress = "00:00:00:00:00:02"
        ),
        BluetoothDeviceInfo(
            name = "Device 4",
            macAddress = "00:00:00:00:00:03"
        ),
    )
)