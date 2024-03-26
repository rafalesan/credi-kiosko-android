package com.rafalesan.credikiosko.core.commons.presentation.composables

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction

@Composable
fun OutlinedTextFieldClickable(
    modifier: Modifier = Modifier,
    value: String,
    errorStringId: Int? = null,
    label: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {

    OutlinedTextFieldWithError(
        modifier = modifier,
        enabled = false,
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = errorStringId?.let {
                MaterialTheme.colorScheme.error
            } ?: MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = Color.Transparent,
            disabledBorderColor = errorStringId?.let {
                MaterialTheme.colorScheme.error
            } ?: MaterialTheme.colorScheme.outline,
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            disabledLabelColor = errorStringId?.let {
                MaterialTheme.colorScheme.error
            } ?: MaterialTheme.colorScheme.onSurfaceVariant,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        value = value,
        label = label,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        errorStringId = errorStringId,
        trailingIcon = trailingIcon,
        onValueChange = {}
    )

}
