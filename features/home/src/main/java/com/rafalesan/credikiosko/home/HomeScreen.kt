package com.rafalesan.credikiosko.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rafalesan.credikiosko.core.R
import com.rafalesan.credikiosko.core.commons.presentation.composables.ToastHandlerComposable
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens

@Deprecated("It should be used HomeScreenNavCompose instead")
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    HomeUI(viewModel = viewModel)
}

@Composable
fun HomeScreenNavCompose(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController
) {
    HomeUI(
        viewModel = viewModel
    )
    ActionHandler(
        viewModel = viewModel,
        navController = navController
    )
}

@Composable
fun HomeUI(
    viewModel: HomeViewModel
) {
    val homeOptions by viewModel.homeOptions.collectAsState()
    val userSessionInfo by viewModel.userSessionInfo.collectAsState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize(),
        constraintSet = homeScreenConstraintSet
    ) {

        Text(
            modifier = Modifier
                .layoutId(WelcomeLabel),
            text = stringResource(id = R.string.welcome_x, userSessionInfo.name),
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            modifier = Modifier
                .layoutId(BusinessLabel),
            text = userSessionInfo.business.name,
            color = MaterialTheme.colorScheme.onSurface
        )

        Image(
            modifier = Modifier
                .layoutId(ProfilePhoto)
                .size(Dimens.space8x)
                .clip(CircleShape)
                .border(Dimens.space2Unit, Color.Gray, CircleShape),
            painter = painterResource(id = R.drawable.ic_empty_profile_photo),
            contentDescription = stringResource(id = R.string.user_profile_avatar),
            contentScale = ContentScale.Crop,

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
                    viewModel = viewModel
                )
            }
        }


    }

    ToastHandlerComposable(viewModel = viewModel)
}

@Composable
fun HomeOptionItem(homeOption: HomeOption, viewModel: HomeViewModel) {

    Card(
        modifier = Modifier
            .aspectRatio(1.0f)
            .fillMaxSize()
            .clickable {
                viewModel.perform(
                    HomeEvent.HomeOptionSelected(homeOption)
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

@Composable
fun ActionHandler(
    viewModel: HomeViewModel,
    navController: NavHostController
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.action.collect { action ->
            when(action) {
                is HomeAction.NavigateTo -> navController.navigate(action.route)
            }
        }
    }
}
