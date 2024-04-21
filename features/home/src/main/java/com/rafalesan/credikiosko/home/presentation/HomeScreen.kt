package com.rafalesan.credikiosko.home.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CreditCard
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rafalesan.credikiosko.core.commons.presentation.composables.ToastHandlerComposable
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens
import com.rafalesan.credikiosko.home.R
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
                            R.string.products,
                            Icons.Filled.Category,
                            "products"
                        ),
                        HomeOption(
                            R.string.customers,
                            Icons.Filled.Groups2
                        ),
                        HomeOption(
                            R.string.credits,
                            Icons.Filled.CreditCard
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

            val screenWidth = LocalConfiguration.current.screenWidthDp.dp

            LazyVerticalGrid(
                modifier = Modifier
                    .layoutId(OptionsList)
                    .wrapContentSize()
                    .padding(horizontal = Dimens.space2x),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(Dimens.space2x),
                horizontalArrangement = Arrangement.spacedBy(Dimens.space2x)
            ) {
                items(
                    homeOptions.size,
                    span = {
                        if (
                            homeOptions.size % 2 != 0 &&
                            homeOptions.lastIndex == it
                        ) {
                            GridItemSpan(2)
                        } else {
                            GridItemSpan(1)
                        }
                    }
                ) { index ->

                    if (
                        homeOptions.lastIndex == index &&
                        homeOptions.size % 2 != 0
                    ) {

                        HomeOptionItem(
                            modifier = Modifier
                                .padding(horizontal = (screenWidth / 4)),
                            homeOption = homeOptions[index],
                            onOptionPressed = onOptionPressed
                        )

                    } else {
                        HomeOptionItem(
                            homeOption = homeOptions[index],
                            onOptionPressed = onOptionPressed
                        )
                    }

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
    modifier: Modifier = Modifier,
    homeOption: HomeOption,
    onOptionPressed: (HomeOption) -> Unit
) {

    Card(
        modifier = modifier
            .aspectRatio(1.0f)
            //.fillMaxSize()
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
