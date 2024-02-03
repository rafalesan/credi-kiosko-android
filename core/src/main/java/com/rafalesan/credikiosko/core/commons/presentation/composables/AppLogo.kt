package com.rafalesan.credikiosko.core.commons.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.rafalesan.credikiosko.core.R

@Composable
fun AppLogo(
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.ic_app),
        contentDescription = stringResource(id = R.string.app_name)
    )
}