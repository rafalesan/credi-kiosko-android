package com.rafalesan.credikiosko.home.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Groups2
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rafalesan.credikiosko.core.commons.presentation.composables.ToastHandlerComposable
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens
import com.rafalesan.credikiosko.core.R as CoreR

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val viewState = viewModel.viewState.collectAsState()

    HomeUI(
        viewState = viewState,
        onOptionPressed = { homeOption ->
            viewModel.perform(
                HomeEvent.HomeOptionSelected(homeOption)
            )
        }
    )

    ActionHandler(
        viewModel = viewModel,
        navController = navController
    )
    ToastHandlerComposable(viewModel = viewModel)
}

@Preview
@Composable
fun HomeUIPreview() {
    HomeUI(
        viewState = remember {
            mutableStateOf(
                HomeState(
                    homeOptions = listOf(
                        HomeOption(
                            com.rafalesan.credikiosko.home.R.string.products,
                            Icons.Filled.Category,
                            "products"
                        ),
                        HomeOption(
                            com.rafalesan.credikiosko.home.R.string.customers,
                            Icons.Filled.Groups2
                        )
                    ),
                    businessName = "Kiosko Alegría"
                )
            )
        },
        onOptionPressed = {}
    )
}

@Composable
fun HomeUI(
    viewState: State<HomeState>,
    onOptionPressed: (HomeOption) -> Unit
) {
    val homeOptions by remember {
        derivedStateOf { viewState.value.homeOptions }
    }

    val businessName by remember {
        derivedStateOf { viewState.value.businessName }
    }

    val appVersion by remember {
        derivedStateOf { viewState.value.appVersion }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize(),
            constraintSet = homeScreenConstraintSet
        ) {

            Text(
                modifier = Modifier
                    .layoutId(BusinessNameLabel),
                text = businessName,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineMedium
            )

            LazyVerticalGrid(
                modifier = Modifier
                    .layoutId(OptionsList),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(Dimens.space2x),
                verticalArrangement = Arrangement.spacedBy(Dimens.space2x),
                horizontalArrangement = Arrangement.spacedBy(Dimens.space2x)
            ) {
                items(items = homeOptions) { homeOption ->
                    HomeOptionItem(
                        homeOption = homeOption,
                        onOptionPressed = onOptionPressed
                    )
                }
            }

            Text(
                modifier = Modifier
                    .layoutId(CrediKioskoLabel),
                text = stringResource(id = CoreR.string.app_name),
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                modifier = Modifier
                    .layoutId(VersionLabel),
                text = appVersion,
                color = MaterialTheme.colorScheme.onSurface
            )

        }
    }

}

@Composable
fun HomeOptionItem(
    homeOption: HomeOption,
    onOptionPressed: (HomeOption) -> Unit
) {

    Card(
        modifier = Modifier
            .aspectRatio(1.0f)
            .fillMaxSize()
            .clickable {
                onOptionPressed(homeOption)
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

@Composable
fun ActionHandler(
    viewModel: HomeViewModel,
    navController: NavHostController
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.action.collect { action ->
            when (action) {
                is HomeAction.NavigateTo -> navController.navigate(action.route)
            }
        }
    }
}
