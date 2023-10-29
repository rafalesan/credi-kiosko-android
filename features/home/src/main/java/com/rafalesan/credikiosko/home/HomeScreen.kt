package com.rafalesan.credikiosko.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.rafalesan.credikiosko.core.commons.presentation.composables.ToastHandlerComposable
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens

@Deprecated("It should be used HomeScreenNavCompose instead")
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    HomeUI(viewModel = viewModel)
}

@Composable
fun HomeScreenNavCompose(
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomeUI(viewModel = viewModel)
}

@Composable
fun HomeUI(viewModel: HomeViewModel) {
    val homeOptions = viewModel.homeOptions.collectAsState().value

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(Dimens.space2x),
        verticalArrangement = Arrangement.spacedBy(Dimens.space2x),
        horizontalArrangement = Arrangement.spacedBy(Dimens.space2x)
    ) {
        items(items = homeOptions) { homeOption ->
            HomeOptionItem(
                homeOption = homeOption,
                viewModel = viewModel
            )
        }
    }

    ToastHandlerComposable(viewModel = viewModel)
}

@Composable
fun HomeOptionItem(homeOption: HomeOption, viewModel: HomeViewModel) {
    
    Card (
        modifier = Modifier
            .aspectRatio(1.0f)
            .fillMaxSize()
            .clickable {
                viewModel.perform(
                    HomeAction.HomeOptionSelected(homeOption)
                )
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(Dimens.space6x)
                    .padding(horizontal = Dimens.space4x),
                imageVector = homeOption.iconVector,
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(Dimens.space2x))

            Text(
                text = stringResource(id = homeOption.optionNameResId),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
    
}
