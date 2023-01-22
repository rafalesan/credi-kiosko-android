package com.rafalesan.credikiosko.core.commons.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens

@ExperimentalComposeUiApi
@Composable
fun IconDialog(
    iconPainter: Painter,
    title: String,
    description: String,
    positiveButton: @Composable () -> Unit,
    negativeButton: @Composable (() -> Unit)? = null,
    iconColorFilter: ColorFilter? = null
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
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Image(
                    modifier = Modifier
                        .size(Dimens.iconDialogSize)
                        .padding(top = Dimens.space2x),
                    painter = iconPainter,
                    contentDescription = null,
                    colorFilter = iconColorFilter
                )
                Text(
                    modifier = Modifier
                        .padding(top = Dimens.space2x)
                        .padding(horizontal = Dimens.space2x),
                    text = description,
                    color = MaterialTheme.colors.onSurface,
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
                    positiveButton()
                }
            }
        }
    }

}
