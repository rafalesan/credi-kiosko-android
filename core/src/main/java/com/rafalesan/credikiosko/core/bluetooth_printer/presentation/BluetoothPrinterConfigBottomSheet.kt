@file:OptIn(ExperimentalMaterial3Api::class)

package com.rafalesan.credikiosko.core.bluetooth_printer.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Mouse
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.rafalesan.credikiosko.core.R
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.BluetoothDevice
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.DeviceType
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.DeviceType.Computer
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.DeviceType.Controller
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.DeviceType.Headphones
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.DeviceType.Keyboard
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.DeviceType.Mouse
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.DeviceType.Phone
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.DeviceType.Printer
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.DeviceType.Unknown
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.DeviceType.Watch
import com.rafalesan.credikiosko.core.commons.presentation.theme.CrediKioskoTheme
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens
import timber.log.Timber

@SuppressLint("MissingPermission")
@Composable
fun BluetoothPrinterConfigBottomSheet(
    onDismissCallback: (Boolean) -> Unit
) {

    val viewModel: BluetoothPrinterConfigViewModel = hiltViewModel()

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.RESUMED -> {
                viewModel.perform(BluetoothPrinterConfigEvent.RefreshBluetoothDevices)
            }
            else -> {
                Timber.d("No operation for: $lifecycleState")
            }
        }
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
            },
            onShowAllDevicesChanged = {
                viewModel.perform(BluetoothPrinterConfigEvent.ShowAllDevicesChanged(it))
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
    onBluetoothDevicePressed: (BluetoothDevice) -> Unit = {},
    onShowAllDevicesChanged: (Boolean) -> Unit = {}
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

    val showAllBluetoothDevices by remember {
        derivedStateOf {
            viewState.value.showAllBluetoothDevices
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier
                .padding(horizontal = Dimens.space2x)
                .padding(bottom = Dimens.spaceDefault),
            text = stringResource(id = R.string.select_the_printer),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .padding(bottom = Dimens.spaceDefault)
                .clickable {
                    onShowAllDevicesChanged(!showAllBluetoothDevices)
                }
                .fillMaxWidth()
                .padding(
                    horizontal = Dimens.space2x,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = stringResource(id = R.string.show_all_devices_not_only_printers),
            )
            Switch(
                checked = showAllBluetoothDevices,
                onCheckedChange = onShowAllDevicesChanged
            )
        }

        Divider(color = Color.LightGray, thickness = Dimens.dividerThickness)

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

            item {
                BindNewBluetoothDeviceItem()
            }
        }

    }

}


@SuppressLint("MissingPermission")
@Composable
fun BluetoothDeviceItem(
    bluetoothDevice: BluetoothDevice,
    onBluetoothDevicePressed: (BluetoothDevice) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onBluetoothDevicePressed(bluetoothDevice)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            modifier = Modifier.padding(start = Dimens.space2x),
            imageVector = getBluetoothIcon(bluetoothDevice.type),
            contentDescription = stringResource(id = R.string.bluetooth_icon)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
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

}

@Composable
fun BindNewBluetoothDeviceItem() {

    val context = LocalContext.current

    Text(
        modifier = Modifier
            .clickable {
                openBluetoothSettings(context)
            }
            .fillMaxWidth()
            .padding(vertical = Dimens.spaceDefault),
        text = stringResource(id = R.string.bind_new_device_desc),
        textAlign = TextAlign.Center
    )
}

fun openBluetoothSettings(context: Context) {
    val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
    context.startActivity(intent)
}

@Composable
private fun getBluetoothIcon(deviceType: DeviceType): ImageVector {
    return when (deviceType) {
        Unknown -> Icons.Filled.Bluetooth
        Printer -> Icons.Filled.Print
        Keyboard -> Icons.Filled.Keyboard
        Mouse -> Icons.Filled.Mouse
        Controller -> ImageVector.vectorResource(id = R.drawable.stadia_controller)
        Headphones -> Icons.Filled.Headphones
        Watch -> Icons.Filled.Watch
        Computer -> Icons.Filled.Computer
        Phone -> Icons.Filled.Smartphone
    }
}

private val MOCKED_BLUETOOTH_DEVICES_FOR_PREVIEW = BluetoothPrinterConfigViewState(
    bondedBluetoothDevices = listOf(
        BluetoothDevice(
            name = "Device 1",
            macAddress = "00:00:00:00:00:00",
            type = Printer
        ),
        BluetoothDevice(
            name = "Device 2",
            macAddress = "00:00:00:00:00:01",
            type = Keyboard
        ),
        BluetoothDevice(
            name = "Device 3",
            macAddress = "00:00:00:00:00:02",
            type = Controller
        ),
        BluetoothDevice(
            name = "Device 4",
            macAddress = "00:00:00:00:00:03",
            type = Headphones
        ),
        BluetoothDevice(
            name = "Device 5",
            macAddress = "00:00:00:00:00:04",
            type = Watch
        ),
        BluetoothDevice(
            name = "Device 6",
            macAddress = "00:00:00:00:00:05",
            type = Mouse
        ),
        BluetoothDevice(
            name = "Device 7",
            macAddress = "00:00:00:00:00:06",
            type = Computer
        ),
        BluetoothDevice(
            name = "Device 8",
            macAddress = "00:00:00:00:00:07",
            type = Phone
        ),
        BluetoothDevice(
            name = "Device 9",
            macAddress = "00:00:00:00:00:08",
            type = Unknown
        ),
    )
)