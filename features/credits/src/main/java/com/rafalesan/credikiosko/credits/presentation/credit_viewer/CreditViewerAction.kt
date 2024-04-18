package com.rafalesan.credikiosko.credits.presentation.credit_viewer

sealed class CreditViewerAction {
    data object CheckBluetoothPermissionAndAvailability : CreditViewerAction()
    data object OpenAppPermissionsSettings : CreditViewerAction()
}
