package com.rafalesan.credikiosko.core.commons.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens

@Composable
fun CommonDialog(
    title: String,
    descriptionMessage: String,
    positiveButton: @Composable () -> Unit,
    negativeButton: @Composable (() -> Unit)? = null,
    neutralButton: @Composable (() -> Unit)? = null,
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
        ),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.space2x),
            shape = RoundedCornerShape(Dimens.dialogBorderRadius)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = Dimens.space2x)
                        .padding(horizontal = Dimens.space2x),
                    text = title,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier
                        .padding(top = Dimens.space2x)
                        .padding(horizontal = Dimens.space2x),
                    text = descriptionMessage,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.spaceDefault),
                    horizontalArrangement = Arrangement.End,
                ) {
                    negativeButton?.invoke()
                    Spacer(modifier = Modifier.width(Dimens.space12units))
                    neutralButton?.let {
                        neutralButton.invoke()
                        Spacer(modifier = Modifier.width(Dimens.space12units))
                    }
                    positiveButton()
                }
            }
        }
    }
}