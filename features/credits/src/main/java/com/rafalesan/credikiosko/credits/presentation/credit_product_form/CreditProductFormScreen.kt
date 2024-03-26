@file:OptIn(ExperimentalMaterial3Api::class)

package com.rafalesan.credikiosko.credits.presentation.credit_product_form

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rafalesan.credikiosko.core.commons.creditProductNavResultKey
import com.rafalesan.credikiosko.core.commons.emptyString
import com.rafalesan.credikiosko.core.commons.presentation.composables.OutlinedTextFieldClickable
import com.rafalesan.credikiosko.core.commons.presentation.composables.OutlinedTextFieldWithError
import com.rafalesan.credikiosko.core.commons.presentation.composables.ToastHandlerComposable
import com.rafalesan.credikiosko.core.commons.presentation.extensions.CollectNavigationBackResult
import com.rafalesan.credikiosko.core.commons.presentation.extensions.popBackStackWithResult
import com.rafalesan.credikiosko.core.commons.presentation.models.ProductParcelable
import com.rafalesan.credikiosko.core.commons.presentation.theme.CrediKioskoTheme
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens
import com.rafalesan.credikiosko.core.commons.productNavResultKey
import com.rafalesan.credikiosko.credits.R
import com.rafalesan.credikiosko.core.R as CoreR

@Composable
fun CreditProductFormScreen(
    navHostController: NavHostController,
    viewModel: CreditProductFormViewModel = hiltViewModel()
) {

    CreditProductFormUI(
        viewState = viewModel.viewState.collectAsState(),
        onBackPressed = { navHostController.navigateUp() },
        onProductSelectorPressed = { viewModel.perform(CreditProductFormEvent.ProductSelectorPressed) },
        onProductPriceChanged = { viewModel.perform(CreditProductFormEvent.ProductPriceChanged(it)) },
        onProductsQuantityChanged = { viewModel.perform(CreditProductFormEvent.ProductsQuantityChanged(it)) },
        onAddCreditProduct = { viewModel.perform(CreditProductFormEvent.AddCreditProductLine) }
    )

    NavigationBackResultsHandler(
        navController = navHostController,
        viewModel = viewModel
    )

    ActionHandler(
        navHostController,
        viewModel
    )

    ToastHandlerComposable(viewModel = viewModel)

}

@Preview
@Composable
fun CreditProductFormUIPreview() {
    CrediKioskoTheme {
        CreditProductFormUI(
            viewState = remember {
                mutableStateOf(
                    CreditProductFormViewState(
                        productSelectedError = R.string.empty_product_validation_desc,
                        totalAmount = "100.00"
                    )
                )
            }
        )
    }
}

@Composable
fun CreditProductFormUI(
    viewState: State<CreditProductFormViewState>,
    onBackPressed: () -> Unit = {},
    onProductSelectorPressed: () -> Unit = {},
    onProductPriceChanged: (String) -> Unit = {},
    onProductsQuantityChanged: (String) -> Unit = {},
    onAddCreditProduct: () -> Unit = {}
) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onSecondary
                ),
                title = {
                    Text(stringResource(id = R.string.product_to_lend))
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Filled.ArrowBack, stringResource(id = com.rafalesan.credikiosko.core.R.string.navigate_back))
                    }
                }
            )
        },
        floatingActionButton = {

            val totalAmount by remember {
                derivedStateOf { viewState.value.totalAmount }
            }

            val addButtonText = if (totalAmount.isBlank()) {
                stringResource(id = R.string.add_product)
            } else {
                stringResource(id = R.string.add_x, totalAmount)
            }

            ExtendedFloatingActionButton(
                onClick = onAddCreditProduct,
                icon = { Icon(Icons.Filled.Check, stringResource(id = R.string.add_product_to_credit)) },
                text = { Text(addButtonText) },
                shape = RoundedCornerShape(16.dp),
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            ProductSelectorInput(
                viewState = viewState,
                onProductSelectorPressed = onProductSelectorPressed
            )

            ProductPriceInput(
                viewState = viewState,
                onProductPriceChanged = onProductPriceChanged
            )

            ProductsQuantityInput(
                viewState = viewState,
                onProductsQuantityChanged = onProductsQuantityChanged
            )

        }

    }

}

@Composable
fun ProductSelectorInput(
    viewState: State<CreditProductFormViewState>,
    onProductSelectorPressed: () -> Unit,
) {

    val productNameSelectedText by remember {
        derivedStateOf {
            viewState.value.productSelected?.name
        }
    }

    val productSelectedError by remember {
        derivedStateOf {
            viewState.value.productSelectedError
        }
    }

    OutlinedTextFieldClickable(
        modifier = Modifier
            .padding(horizontal = Dimens.space2x)
            .padding(top = Dimens.space2x)
            .clip(OutlinedTextFieldDefaults.shape)
            .clickable {
                onProductSelectorPressed.invoke()
            },
        label = { Text(text = stringResource(id = R.string.product)) },
        value = productNameSelectedText ?: stringResource(id = R.string.no_product_selected),
        errorStringId = productSelectedError,
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = stringResource(id = R.string.product)
            )
        },
    )

}

@Composable
fun ProductPriceInput(
    viewState: State<CreditProductFormViewState>,
    onProductPriceChanged: (String) -> Unit
) {

    val productPriceText = remember {
        derivedStateOf {
            viewState.value.productPrice ?: emptyString
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
            .padding(top = Dimens.space2x),
        value = productPriceText.value,
        label = { Text(text = stringResource(id = CoreR.string.product_price)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        ),
        errorStringId = productPriceError,
        onValueChange = onProductPriceChanged
    )

}
@Composable
fun ProductsQuantityInput(
    viewState: State<CreditProductFormViewState>,
    onProductsQuantityChanged: (String) -> Unit
) {

    val productsQuantityText = remember {
        derivedStateOf {
            viewState.value.productsQuantity
        }
    }
    val productsQuantityError by remember {
        derivedStateOf {
            viewState.value.productsQuantityError
        }
    }

    OutlinedTextFieldWithError(
        modifier = Modifier
            .padding(horizontal = Dimens.space2x)
            .padding(top = Dimens.space2x),
        value = productsQuantityText.value,
        label = { Text(text = stringResource(id = R.string.quantity)) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        ),
        errorStringId = productsQuantityError,
        onValueChange = onProductsQuantityChanged
    )

}

@Composable
fun ActionHandler(
    navController: NavHostController,
    viewModel: CreditProductFormViewModel
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.action.collect { action ->
            when (action) {
                CreditProductFormAction.ShowProductSelector -> {
                    navController.navigate("product_selector")
                }

                is CreditProductFormAction.ReturnCreditProductLine -> {
                    navController.popBackStackWithResult(
                        creditProductNavResultKey,
                        action.creditProduct
                    )
                }
            }
        }
    }

}

@Composable
fun NavigationBackResultsHandler(
    navController: NavHostController,
    viewModel: CreditProductFormViewModel
) {

    navController.CollectNavigationBackResult(
        key = productNavResultKey,
        initialValue = ProductParcelable(),
        resultCallback = { productBackResult ->
            viewModel.perform(CreditProductFormEvent.SetProduct(productBackResult))
        }
    )

}