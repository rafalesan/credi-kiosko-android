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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

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
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .padding(horizontal = 16.dp),
                    text = title,
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Image(
                    modifier = Modifier
                        .size(80.dp)
                        .padding(top = 16.dp),
                    painter = iconPainter,
                    contentDescription = null,
                    colorFilter = iconColorFilter
                )
                Text(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .padding(horizontal = 16.dp),
                    text = description,
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Center,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp)
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.End,
                ) {
                    negativeButton?.invoke()
                    Spacer(modifier = Modifier.width(12.dp))
                    positiveButton()
                }
            }
        }
    }

}
