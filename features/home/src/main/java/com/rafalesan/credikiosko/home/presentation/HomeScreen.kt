package com.rafalesan.credikiosko.home.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Groups2
import androidx.compose.material.icons.outlined.Store
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rafalesan.credikiosko.core.commons.presentation.composables.OutlinedTextFieldWithError
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
        },
        onEditBusinessName = {
            viewModel.perform(
                HomeEvent.EditBusinessName
            )
        },
        onBusinessNameChanged = {
            viewModel.perform(
                HomeEvent.BusinessNameInputChanged(it)
            )
        },
        onCancelBusinessNameEdit = {
            viewModel.perform(HomeEvent.CancelBusinessNameEdit)
        },
        onSubmitBusinessNameEdit = {
            viewModel.perform(HomeEvent.SubmitBusinessName)
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
                    isShowingEditBusinessNameDialog = true,
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
        }
    )
}

@Composable
fun HomeUI(
    viewState: State<HomeState>,
    onOptionPressed: (HomeOption) -> Unit = {},
    onEditBusinessName: () -> Unit =  {},
    onBusinessNameChanged: (String) -> Unit = {},
    onCancelBusinessNameEdit: () -> Unit = {},
    onSubmitBusinessNameEdit: () -> Unit = {}
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

    EditBusinessNameDialog(
        viewState = viewState,
        onBusinessNameChanged = onBusinessNameChanged,
        onCancelBusinessNameEdit = onCancelBusinessNameEdit,
        onSubmitBusinessNameEdit = onSubmitBusinessNameEdit
    )

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

            IconButton(
                modifier = Modifier
                    .layoutId(EditBusinessNameButton),
                onClick = onEditBusinessName
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = stringResource(id = R.string.edit_business_name)
                )
            }

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
fun EditBusinessNameDialog(
    viewState: State<HomeState>,
    onBusinessNameChanged: (String) -> Unit,
    onCancelBusinessNameEdit: () -> Unit,
    onSubmitBusinessNameEdit: () -> Unit
) {
    val isShowingEditBusinessNameDialog by remember {
        derivedStateOf {
            viewState.value.isShowingEditBusinessNameDialog
        }
    }

    if (isShowingEditBusinessNameDialog) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
            ),
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.space2x),
                shape = RoundedCornerShape(Dimens.dialogBorderRadius)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = Dimens.space2x)
                            .padding(horizontal = Dimens.space2x),
                        text = stringResource(id = R.string.type_new_business_name),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    BusinessNameInput(
                        viewState = viewState,
                        onBusinessNameChanged = onBusinessNameChanged
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimens.spaceDefault),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        TextButton(
                            onClick = onCancelBusinessNameEdit
                        ) {
                            Text(text = stringResource(id = CoreR.string.cancel))
                        }
                        Spacer(modifier = Modifier.width(Dimens.space12units))
                        TextButton(
                            onClick = onSubmitBusinessNameEdit
                        ) {
                            Text(text = stringResource(id = CoreR.string.accept))
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun BusinessNameInput(
    viewState: State<HomeState>,
    onBusinessNameChanged: (String) -> Unit
) {
    val businessNameText = remember {
        derivedStateOf { viewState.value.inputBusinessName }
    }
    val businessNameError = remember {
        derivedStateOf { viewState.value.inputBusinessNameError }
    }

    OutlinedTextFieldWithError(
        modifier = Modifier
            .padding(horizontal = Dimens.space2x)
            .padding(top = Dimens.space2x),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        value = businessNameText.value,
        errorStringId = businessNameError.value,
        onValueChange = onBusinessNameChanged,
        label = { Text(text = stringResource(id = CoreR.string.kiosk_name)) },
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Store,
                contentDescription = stringResource(id = CoreR.string.kiosk)
            )
        }
    )
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
