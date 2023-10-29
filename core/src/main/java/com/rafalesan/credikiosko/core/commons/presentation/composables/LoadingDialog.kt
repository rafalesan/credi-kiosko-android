package com.rafalesan.credikiosko.core.commons.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoadingDialog(loadingText: String) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
        ),
    ) {
        Surface(
            shape = RoundedCornerShape(Dimens.dialogBorderRadius)
        ) {
            Column(
                modifier = Modifier.padding(Dimens.space4x),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(Dimens.space4x))
                Text(
                    text = loadingText,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}