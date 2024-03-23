package com.rafalesan.credikiosko.customers.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.rafalesan.credikiosko.core.commons.domain.entity.Customer
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens
import com.rafalesan.credikiosko.customers.R

@Composable
fun CustomerItem(
    customer: Customer,
    onItemPressed: (Customer) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemPressed(customer) }
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = Dimens.space2x)
                .padding(top = Dimens.space2x),
            text = customer.name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        val nickname = customer.nickname?.let {
            if (it.isBlank()) {
                return@let stringResource(id = R.string.no_nickname)
            }
            return@let stringResource(
                id = R.string.nickname_x,
                it
            )
        } ?: stringResource(id = R.string.no_nickname)

        Text(
            modifier = Modifier
                .padding(horizontal = Dimens.space2x)
                .padding(top = Dimens.spaceDefault, bottom = Dimens.space2x),
            text = nickname
        )
    }
}
