package com.rafalesan.credikiosko.core.commons.presentation.composables

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.rafalesan.credikiosko.core.R

@Composable
fun DeleteButton(
    modifier: Modifier = Modifier,
    onDeletePressed: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error
        ),
        onClick = onDeletePressed
    ) {
        Text(
            text = stringResource(id = R.string.delete).uppercase(),
            color = MaterialTheme.colorScheme.onError
        )
    }
}