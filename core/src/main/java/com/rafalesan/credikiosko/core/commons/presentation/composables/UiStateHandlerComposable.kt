@file:OptIn(ExperimentalComposeUiApi::class)

package com.rafalesan.credikiosko.core.commons.presentation.composables

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.rafalesan.credikiosko.core.R
import com.rafalesan.credikiosko.core.commons.presentation.base.BaseViewModel
import com.rafalesan.credikiosko.core.commons.presentation.utils.UiState
import timber.log.Timber

@Composable
fun UiStateHandlerComposable(viewModel: BaseViewModel) {

    val uiState = viewModel.uiState.collectAsState(initial = UiState.Idle).value

    when (uiState) {
        is UiState.Loading -> Loading(uiStateLoading = uiState)
        is UiState.ApiError -> ApiErrorDialog(uiStateApiError = uiState)
        UiState.NoInternet -> NoInternetDialog()
        UiState.ApiNotAvailable -> ApiNotAvailableDialog()
        UiState.UnknownError -> UnknownErrorDialog()
        UiState.Idle -> { Timber.d("UI is idle") }
    }

}

@Composable
fun Loading(uiStateLoading: UiState.Loading) {
    if(uiStateLoading.isLoading) {
        LoadingDialog(
            loadingText = stringResource(
                id = uiStateLoading.stringResMessageId ?: R.string.loading
            )
        )
    }
}

@Composable
fun NoInternetDialog() {
    BaseUiStateDialog(
        iconResId = R.drawable.ic_error,
        titleResId = R.string.no_internet_connection,
        description =stringResource(id = R.string.no_internet_connection_description),
    )
}

@Composable
fun ApiErrorDialog(uiStateApiError: UiState.ApiError) {

    BaseUiStateDialog(
        iconResId = R.drawable.ic_error,
        description = uiStateApiError.message
    )

}

@Composable
fun ApiNotAvailableDialog() {
    BaseUiStateDialog(
        iconResId = R.drawable.ic_cloud_off,
        description = stringResource(id = R.string.server_not_available_or_bad_internet_connection)
    )
}

@Composable
fun UnknownErrorDialog() {
    BaseUiStateDialog(
        iconResId = R.drawable.ic_device_unknown,
        description = stringResource(id = R.string.unknown_error_description)
    )
}

@Composable
fun BaseUiStateDialog(
    @DrawableRes iconResId: Int,
    @StringRes titleResId: Int = R.string.message,
    description: String
) {
    val openDialog = remember { mutableStateOf(true) }
    if(openDialog.value) {
        IconDialog(
            iconPainter = painterResource(id = iconResId),
            iconColorFilter = ColorFilter.tint(colorResource(id = R.color.orange)),
            title = stringResource(id = titleResId),
            description = description,
            positiveButton = {
                TextButton(onClick = { openDialog.value = false }) {
                    Text(text = stringResource(id = R.string.accept))
                }
            }
        )
    }
}
