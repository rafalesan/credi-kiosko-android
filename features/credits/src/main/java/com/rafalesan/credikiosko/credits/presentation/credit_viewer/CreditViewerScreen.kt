@file:OptIn(ExperimentalMaterial3Api::class)

package com.rafalesan.credikiosko.credits.presentation.credit_viewer

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Print
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rafalesan.credikiosko.core.bluetooth_printer.presentation.BluetoothPrinterConfigBottomSheet
import com.rafalesan.credikiosko.core.commons.domain.entity.CreditProduct
import com.rafalesan.credikiosko.core.commons.emptyString
import com.rafalesan.credikiosko.core.commons.presentation.composables.CommonDialog
import com.rafalesan.credikiosko.core.commons.presentation.composables.DeleteButton
import com.rafalesan.credikiosko.core.commons.presentation.composables.DotBetweenTextUI
import com.rafalesan.credikiosko.core.commons.presentation.composables.LoadingDialog
import com.rafalesan.credikiosko.core.commons.presentation.composables.ToastHandlerComposable
import com.rafalesan.credikiosko.core.commons.presentation.theme.CrediKioskoTheme
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens
import com.rafalesan.credikiosko.core.commons.presentation.utils.DateFormatUtil
import com.rafalesan.credikiosko.credits.R
import timber.log.Timber
import com.rafalesan.credikiosko.core.R as CoreR

@Composable
fun CreditViewerScreen(
    navController: NavHostController,
    viewModel: CreditViewerViewModel = hiltViewModel()
) {

    CreditViewerUI(
        viewState = viewModel.viewState.collectAsState(),
        onBackPressed = { navController.navigateUp() },
        onPrintButtonPressed = {
            viewModel.perform(
                CreditViewerEvent.PrintCredit(checkBluetoothAvailability = true)
            )
        },
        onCancelPrintingRetry = { viewModel.perform(CreditViewerEvent.CancelPrintingRetry) },
        onRetryPrinting = { viewModel.perform(CreditViewerEvent.RetryPrinting) },
        onCancelPrinterConfiguration = { viewModel.perform(CreditViewerEvent.CancelPrinterConfiguration) },
        onStartPrinterConfiguration = { viewModel.perform(CreditViewerEvent.StartPrinterConfiguration) },
        onDismissPrintersConfiguration = { isPrinterConfigured ->
            viewModel.perform(CreditViewerEvent.DismissPrinterConfiguration(isPrinterConfigured))
        },
        onCancelBluetoothPermissionRequestFromSettings = {
            viewModel.perform(CreditViewerEvent.CancelBluetoothPermissionRequestFromSettings)
        },
        onRequestBluetoothPermissionFromSettings = {
            viewModel.perform(CreditViewerEvent.RequestBluetoothPermissionFromSettings)
        },
        onDeleteCreditPressed = {
            viewModel.perform(CreditViewerEvent.DeleteCredit)
        }
    )

    ActionHandler(
        viewModel = viewModel,
        navController = navController
    )

    ToastHandlerComposable(viewModel = viewModel)

}

@Preview
@Composable
fun CreditViewerUIPreview() {
    CrediKioskoTheme {
        CreditViewerUI(
            viewState = remember {
                mutableStateOf(MOCKED_VIEWER_STATE_FOR_PREVIEW)
            }
        )
    }
}

@Composable
fun CreditViewerUI(
    viewState: State<CreditViewerState>,
    onBackPressed: () -> Unit = {},
    onPrintButtonPressed: () -> Unit = {},
    onCancelPrintingRetry: () -> Unit = {},
    onRetryPrinting: () -> Unit = {},
    onCancelPrinterConfiguration: () -> Unit = {},
    onStartPrinterConfiguration: () -> Unit = {},
    onDismissPrintersConfiguration: (Boolean) -> Unit = {},
    onCancelBluetoothPermissionRequestFromSettings: () -> Unit = {},
    onRequestBluetoothPermissionFromSettings: () -> Unit = {},
    onDeleteCreditPressed: () -> Unit = {}
) {

    val printLoadingTextId by remember {
        derivedStateOf {
            viewState.value.printLoadingStringResId
        }
    }

    val printerConnectionError by remember {
        derivedStateOf {
            viewState.value.printerConnectionError
        }
    }

    val isShowingPrinterNotConfiguredMessage by remember {
        derivedStateOf {
            viewState.value.isShowingPrinterNotConfiguredMessage
        }
    }

    val isShowingPrintersConfiguration by remember {
        derivedStateOf {
            viewState.value.isShowingPrinterConfiguration
        }
    }

    val printerThatConnectionFailed by remember {
        derivedStateOf {
            viewState.value.printerThatConnectionFailed
        }
    }

    printLoadingTextId?.let {
        Timber.d("printLoadingText: ${stringResource(id = it)}")
        LoadingDialog(loadingText = stringResource(id = it))
    }

    printerConnectionError?.let { connectionErrorStringId ->

        CommonDialog(
            title = stringResource(id = R.string.connection_error),
            descriptionMessage = stringResource(id = connectionErrorStringId),
            negativeButton = {
                TextButton(onClick = onCancelPrintingRetry) {
                    Text(text = stringResource(id = CoreR.string.cancel))
                }
            },
            positiveButton = {
                TextButton(onClick = onRetryPrinting) {
                    Text(text = stringResource(id = R.string.retry))
                }
            }
        )

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
                    Text(text = stringResource(id = CoreR.string.cancel))
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

    if (isShowingPrinterNotConfiguredMessage) {

        CommonDialog(
            title = stringResource(id = R.string.printer_not_configured),
            descriptionMessage = stringResource(id = R.string.printer_not_configured_desc),
            negativeButton = {
                TextButton(onClick = onCancelPrinterConfiguration) {
                    Text(text = stringResource(id = CoreR.string.cancel))
                }
            },
            positiveButton = {
                TextButton(onClick = onStartPrinterConfiguration) {
                    Text(text = stringResource(id = R.string.configure))
                }
            }
        )

    }

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
                    Text(text = stringResource(id = CoreR.string.cancel))
                }
            },
            positiveButton = {
                TextButton(onClick = onRequestBluetoothPermissionFromSettings) {
                    Text(text = stringResource(id = R.string.go_to_settings))
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onSecondary
                ),
                title = {
                    Text(stringResource(id = R.string.credit_detail))
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Filled.ArrowBack, stringResource(id = CoreR.string.navigate_back))
                    }
                }
            )
        },
        floatingActionButton = {

            ExtendedFloatingActionButton(
                onClick = onPrintButtonPressed,
                icon = { Icon(Icons.Filled.Print, stringResource(id = R.string.print)) },
                text = { Text(stringResource(id = R.string.print)) },
                shape = RoundedCornerShape(16.dp),
            )
        }
    ) { innerPadding ->

        val creditProducts by remember {
            derivedStateOf {
                viewState.value.creditProducts
            }
        }

        val creditTotal by remember {
            derivedStateOf {
                viewState.value.creditTotal ?: emptyString
            }
        }

        if (isShowingPrintersConfiguration) {
            BluetoothPrinterConfigBottomSheet(
                onDismissCallback = onDismissPrintersConfiguration
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = Dimens.space10x)
        ) {

            item {
                CreditViewerHeader(viewState = viewState)
            }

            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.space2x)
                        .padding(top = Dimens.space2x, bottom = Dimens.space2x),
                    style = MaterialTheme.typography.titleLarge,
                    text = stringResource(id = CoreR.string.products)
                )
            }

            items(
                creditProducts.size,
                key = { index ->
                    creditProducts[index].id
                }
            ) { index ->
                ProductLineItem(creditProduct = creditProducts[index])
            }

            item {
                TotalCard(creditTotal)
            }

            item {
                DeleteButton(
                    onDeletePressed = onDeleteCreditPressed
                )
            }

        }

    }
}

@Composable
fun TotalCard(
    creditTotal: String
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.space2x)
            .padding(bottom = Dimens.spaceDefault, top = Dimens.spaceDefault)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimens.spaceDefault,
                    vertical = Dimens.spaceDefault
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.End,
                text = stringResource(id = R.string.total)
            )

            Text(
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.End,
                text = stringResource(id = R.string.cordoba_symbol_x, creditTotal)
            )
        }

    }
}

@Composable
fun ProductLineItem(
    creditProduct: CreditProduct
) {

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.space2x)
            .padding(bottom = Dimens.spaceDefault)
    ) {

        Text(
            modifier = Modifier
                .padding(horizontal = Dimens.spaceDefault)
                .padding(top = Dimens.spaceDefault),
            text = creditProduct.productName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        DotBetweenTextUI(
            modifier = Modifier
                .padding(horizontal = Dimens.spaceDefault),
            start = stringResource(id = R.string.quantity_equals),
            end = creditProduct.quantity
        )

        DotBetweenTextUI(
            modifier = Modifier
                .padding(horizontal = Dimens.spaceDefault),
            start = stringResource(id = R.string.unit_price),
            end = stringResource(id = R.string.cordoba_symbol_x, creditProduct.productPrice)
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.spaceDefault)
                .padding(vertical = Dimens.spaceDefault),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.End,
            text = stringResource(id = R.string.cordoba_symbol_x, creditProduct.total)
        )

    }

}

@Composable
fun CreditViewerHeader(
    viewState: State<CreditViewerState>
) {

    val customerName by remember {
        derivedStateOf {
            viewState.value.customerName ?: emptyString
        }
    }

    val creditDate by remember {
        derivedStateOf {
            viewState.value.creditDateTime?.let {
                DateFormatUtil.getUIDateFormatFrom(it)
            } ?: emptyString
        }
    }

    val creditTime by remember {
        derivedStateOf {
            viewState.value.creditDateTime?.let {
                DateFormatUtil.getUITimeFormatFrom(it)
            } ?: emptyString
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.space2x)
            .padding(top = Dimens.space2x)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge,
            text = stringResource(id = R.string.customer)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium,
            text = customerName
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.date_x, creditDate),
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.time_x, creditTime),
        )
    }
}

@Composable
private fun ActionHandler(
    viewModel: CreditViewerViewModel,
    navController: NavHostController
) {

    val context = LocalContext.current
    val bluetoothManager = ContextCompat.getSystemService(context, BluetoothManager::class.java)
    val bluetoothAdapter = bluetoothManager?.adapter ?: run {
        Timber.e("This android devices does not support bluetooth")
        null
    }

    val requestEnableBluetoothLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK) {
            viewModel.perform(
                CreditViewerEvent.PrintCredit(checkBluetoothAvailability = false)
            )
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allPermissionGranted = !permissions.containsValue(false)
        if (allPermissionGranted) {
            if (bluetoothAdapter?.isEnabled == true) {
                viewModel.perform(
                    CreditViewerEvent.PrintCredit(checkBluetoothAvailability = false)
                )
                return@rememberLauncherForActivityResult
            }
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            requestEnableBluetoothLauncher.launch(intent)
        } else {
            viewModel.perform(CreditViewerEvent.BluetoothPermissionDenied)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.action.collect { action ->
            when (action) {
                CreditViewerAction.CheckBluetoothPermissionAndAvailability -> {
                    checkBluetoothPermission(permissionLauncher)
                }
                CreditViewerAction.OpenAppPermissionsSettings -> {
                    openAppSettings(context)
                }
                CreditViewerAction.ReturnToCreditsList -> {
                    navController.navigateUp()
                }
            }
        }
    }

}

private fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts(
        "package",
        context.packageName,
        null
    )
    intent.data = uri
    context.startActivity(intent)
}

private fun checkBluetoothPermission(
    permissionsLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
) {

    val requiredPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        arrayOf(
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN,
        )
    } else {
        arrayOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    permissionsLauncher.launch(requiredPermissions)

}

private val MOCKED_VIEWER_STATE_FOR_PREVIEW = CreditViewerState(
    customerName = "Rafael Antonio Alegría Sánchez",
    creditDateTime = "2023-12-26 23:04:06",
    creditTotal = "161.00",
    creditProducts = listOf(
        CreditProduct(
            id = 1,
            productName = "Almuerzo sencillo",
            productPrice = "70.00",
            quantity = "2",
            total = "140.00"
        ),
        CreditProduct(
            id = 2,
            productName = "Taza de café",
            productPrice = "7.00",
            quantity = "3",
            total = "21.00"
        )
    )
)