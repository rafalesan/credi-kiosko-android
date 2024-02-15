@file:OptIn(ExperimentalMaterial3Api::class)

package com.rafalesan.products.presentation.product_form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rafalesan.credikiosko.core.commons.presentation.composables.OutlinedTextFieldWithError
import com.rafalesan.credikiosko.core.commons.presentation.composables.ToastHandlerComposable
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens
import com.rafalesan.products.R

@Composable
fun ProductFormScreen(
    navController: NavHostController,
    viewModel: ProductFormViewModel = hiltViewModel()
) {

    val viewState = viewModel.viewState.collectAsState()

    ProductFormUI(
        viewState,
        onBackPressed = {
            navController.navigateUp()
        },
        onProductNameChange = {
            viewModel.perform(ProductFormEvent.SetProductName(it))
        },
        onProductPriceChange = {
            viewModel.perform(ProductFormEvent.SetProductPrice(it))
        },
        onSaveProductPressed = {
            viewModel.perform(ProductFormEvent.SaveProduct)
        }
    )

    ActionHandlerComposable(
        navController = navController,
        viewModel = viewModel
    )
    ToastHandlerComposable(viewModel = viewModel)

}

@Preview
@Composable
fun ProductFormUIPreview() {
    ProductFormUI(
        viewState = remember { mutableStateOf(ProductFormState()) },
        {},
        {},
        {},
        {}
    )
}

@Composable
fun ProductFormUI(
    viewState: State<ProductFormState>,
    onBackPressed: () -> Unit,
    onProductNameChange: (String) -> Unit,
    onProductPriceChange: (String) -> Unit,
    onSaveProductPressed: () -> Unit,
) {

    Scaffold(
        topBar = {

            val titleText by remember {
                derivedStateOf {
                    if (viewState.value.isNewProduct) {
                        R.string.new_product
                    } else {
                        R.string.modify_product
                    }
                }
            }

            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onSecondary
                ),
                title = {
                    Column {
                        Text(stringResource(id = titleText))

                        if (!viewState.value.isNewProduct) {
                            Text(
                                stringResource(
                                    R.string.editing_product_x,
                                    viewState.value.productName
                                ),
                                style = MaterialTheme.typography.titleSmall
                            )
                        }

                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Filled.ArrowBack, stringResource(id = com.rafalesan.credikiosko.core.R.string.navigate_back))
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onSaveProductPressed,
                icon = { Icon(Icons.Filled.Save, stringResource(id = R.string.save_product)) },
                text = { Text(stringResource(id = R.string.save_product))},
                shape = RoundedCornerShape(16.dp),
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            ProductNameInput(viewState, onProductNameChange)
            ProductPriceInput(viewState, onProductPriceChange)

        }

    }

}

@Composable
fun ProductNameInput(
    viewState: State<ProductFormState>,
    onProductNameChange: (String) -> Unit
) {

    val productNameText by remember {
        derivedStateOf {
            viewState.value.productName
        }
    }
    val productNameError by remember {
        derivedStateOf {
            viewState.value.productNameError
        }
    }

    OutlinedTextFieldWithError(
        modifier = Modifier
            .padding(horizontal = Dimens.space2x)
            .padding(vertical = Dimens.space2x),
        value = productNameText,
        label = { Text(text = stringResource(id = R.string.product_name)) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        errorStringId = productNameError,
        onValueChange = onProductNameChange
    )

}

@Composable
fun ProductPriceInput(
    viewState: State<ProductFormState>,
    onProductPriceChange: (String) -> Unit
) {

    val productPriceText = remember {
        derivedStateOf {
            viewState.value.productPrice
        }
    }
    val productPriceError by remember {
        derivedStateOf {
            viewState.value.productPriceError
        }
    }

    OutlinedTextFieldWithError(
        modifier = Modifier
            .padding(horizontal = Dimens.space2x)
            .padding(bottom = Dimens.space2x),
        value = productPriceText.value,
        label = { Text(text = stringResource(id = R.string.product_price)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        ),
        errorStringId = productPriceError,
        onValueChange = onProductPriceChange
    )

}

@Composable
fun ActionHandlerComposable(
    navController: NavHostController,
    viewModel: ProductFormViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.action.collect { event ->
            when (event) {
                ProductFormAction.ReturnToProductList -> {
                    navController.navigateUp()
                }
            }
        }
    }
}