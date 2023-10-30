package com.rafalesan.credikiosko.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.rafalesan.credikiosko.R
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens

@Preview
@Composable
fun DrawerContentPreview() {
    DrawerContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent() {
    ModalDrawerSheet(
        modifier = Modifier.requiredWidth(Dimens.drawerWidth)
    ) {
        DrawerHeader()
    }
}

@Composable
fun DrawerHeader() {
    val userName = "Rafael Antonio Alegría Sánchez"
    val userEmail = "rafalesan96@gmail.com"
    val userBusiness = "Kiosko Gloria"
    Column(
        modifier = Modifier.padding(Dimens.space2x)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_empty_profile_photo),
            contentDescription = stringResource(id = R.string.user_profile_avatar),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(Dimens.space8x)
                .clip(CircleShape)
                .border(Dimens.space2Unit, Color.Gray, CircleShape)
        )
        Spacer(modifier = Modifier.height(Dimens.spaceDefault))
        Text(
            text = userName,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = userEmail,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = userBusiness,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Bold
        )
    }

    Divider(thickness = Dimens.space1Unit, color = Color.LightGray)
}