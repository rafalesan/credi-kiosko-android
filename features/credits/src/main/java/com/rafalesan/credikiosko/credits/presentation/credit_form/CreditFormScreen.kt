@file:OptIn(ExperimentalMaterial3Api::class)

package com.rafalesan.credikiosko.credits.presentation.credit_form

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rafalesan.credikiosko.core.commons.creditProductNavKey
import com.rafalesan.credikiosko.core.commons.creditProductNavResultKey
import com.rafalesan.credikiosko.core.commons.customerNavResultKey
import com.rafalesan.credikiosko.core.commons.domain.entity.CreditProduct
import com.rafalesan.credikiosko.core.commons.domain.entity.Customer
import com.rafalesan.credikiosko.core.commons.presentation.composables.DotBetweenTextUI
import com.rafalesan.credikiosko.core.commons.presentation.composables.OutlinedTextFieldWithError
import com.rafalesan.credikiosko.core.commons.presentation.composables.ToastHandlerComposable
import com.rafalesan.credikiosko.core.commons.presentation.extensions.CollectNavigationBackResult
import com.rafalesan.credikiosko.core.commons.presentation.extensions.navigate
import com.rafalesan.credikiosko.core.commons.presentation.models.CreditProductParcelable
import com.rafalesan.credikiosko.core.commons.presentation.models.CustomerParcelable
import com.rafalesan.credikiosko.core.commons.presentation.theme.CrediKioskoTheme
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens
import com.rafalesan.credikiosko.credits.R
import com.rafalesan.credikiosko.core.R as CoreR

@Composable
fun CreditFormScreen(
    navController: NavHostController,
    viewModel: CreditFormViewModel = hiltViewModel()
) {

    val viewState = viewModel.viewState.collectAsState()

    CreditFormUI(
        viewState = viewState,
        onBackPressed = { navController.navigateUp() },
        onCustomerSelectorPressed = { viewModel.perform(CreditFormEvent.CustomerSelectorPressed) },
        onDeleteProductLinePressed = { viewModel.perform(CreditFormEvent.DeleteProductLine(it)) },
        onEditProductLinePressed = { viewModel.perform(CreditFormEvent.EditProductLine(it)) },
        onAddProductLinePressed = { viewModel.perform(CreditFormEvent.AddProductLinePressed) },
        onCreateCredit = { viewModel.perform(CreditFormEvent.CreateCredit) },
    )

    NavigationBackResultsHandler(
        navController = navController,
        viewModel = viewModel
    )

    ActionHandler(
        navController = navController,
        viewModel = viewModel
    )

    ToastHandlerComposable(viewModel = viewModel)

}

@Preview
@Composable
fun CreditFormUIPreview() {
    CrediKioskoTheme {
        CreditFormUI(
            viewState = remember {
                mutableStateOf(MOCKED_CREDITS_FOR_PREVIEW)
            }
        )
    }
}

@Composable
fun CreditFormUI(
    viewState: State<CreditFormState>,
    onBackPressed: () -> Unit = {},
    onCustomerSelectorPressed: () -> Unit = {},
    onDeleteProductLinePressed: (CreditProduct) -> Unit = {},
    onEditProductLinePressed: (CreditProduct) -> Unit = {},
    onAddProductLinePressed: () -> Unit = {},
    onCreateCredit: () -> Unit = {},
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
                    Text(stringResource(id = R.string.new_credit))
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Filled.ArrowBack, stringResource(id = CoreR.string.navigate_back))
                    }
                }
            )
        },
        floatingActionButton = {

            val totalAmount by remember {
                derivedStateOf { viewState.value.totalCreditAmount }
            }

            val addButtonText = if (totalAmount.isBlank()) {
                stringResource(id = R.string.lend)
            } else {
                stringResource(id = R.string.lend_x, totalAmount)
            }

            ExtendedFloatingActionButton(
                onClick = onCreateCredit,
                icon = { Icon(Icons.Filled.Check, stringResource(id = R.string.lend)) },
                text = { Text(addButtonText) },
                shape = RoundedCornerShape(16.dp),
            )
        }
    ) { innerPadding ->

        val productLines by remember {
            derivedStateOf {
                viewState.value.productLines
            }
        }

        val isEmptyProductLines by remember {
            derivedStateOf {
                productLines.isEmpty()
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = Dimens.space10x)
        ) {

            item {
                CustomerSelectorInput(
                    viewState = viewState,
                    onCustomerSelectorPressed
                )
            }

            item {
                Text(
                    modifier = Modifier
                        .padding(horizontal = Dimens.space2x)
                        .padding(vertical = Dimens.space2x),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    text = stringResource(CoreR.string.products),
                )
            }

            if (isEmptyProductLines) {
                item {
                    Text(
                        modifier = Modifier
                            .padding(
                                horizontal = Dimens.space2x,
                                vertical = Dimens.space8x
                            )
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(id = R.string.empty_product_lines_list_desc)
                    )
                }
            }

            items(
                productLines.size,
                key = { index ->
                    productLines[index].productName + index
                }
            ) { index ->
                ProductLineItem(
                    creditProduct = productLines[index],
                    onDeleteProductLinePressed = onDeleteProductLinePressed,
                    onEditProductLinePressed = onEditProductLinePressed
                )
            }

            item {
                AddProductLineButton(onAddProductLinePressed)
            }

        }

    }
}

@Composable
fun CustomerSelectorInput(
    viewState: State<CreditFormState>,
    onCustomerSelectorPressed: () -> Unit,
) {

    val customerNameSelectedText by remember {
        derivedStateOf {
            val nickname = viewState.value.customerSelected?.nickname

            if (nickname.isNullOrBlank()) {
                viewState.value.customerSelected?.name
            } else {
                nickname
            }
        }
    }

    val customerNameSelectedError by remember {
        derivedStateOf {
            viewState.value.customerNameSelectedError
        }
    }

    OutlinedTextFieldWithError(
        modifier = Modifier
            .padding(horizontal = Dimens.space2x)
            .padding(top = Dimens.space2x)
            .clip(OutlinedTextFieldDefaults.shape)
            .clickable {
                onCustomerSelectorPressed.invoke()
            },
        enabled = false,
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = Color.Transparent,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledPrefixColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledSuffixColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        value = customerNameSelectedText ?: stringResource(id = R.string.no_customer_selected),
        label = { Text(text = stringResource(id = R.string.customer)) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        errorStringId = customerNameSelectedError,
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = stringResource(id = R.string.customer)
            )
        },
        onValueChange = {}
    )
}

@Composable
fun ProductLineItem(
    creditProduct: CreditProduct,
    onDeleteProductLinePressed: (CreditProduct) -> Unit,
    onEditProductLinePressed: (CreditProduct) -> Unit,
) {

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.space2x)
            .padding(bottom = Dimens.spaceDefault)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Dimens.spaceDefault)
                .padding(top = Dimens.spaceDefault),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = creditProduct.productName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Row {

                IconButton(
                    modifier = Modifier.size(Dimens.space5x),
                    onClick = { onDeleteProductLinePressed.invoke(creditProduct) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        tint = MaterialTheme.colorScheme.error,
                        contentDescription = stringResource(id = R.string.delete_product_line)
                    )
                }

                IconButton(
                    modifier = Modifier.size(Dimens.space5x),
                    onClick = { onEditProductLinePressed.invoke(creditProduct) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        tint = MaterialTheme.colorScheme.surfaceTint,
                        contentDescription = stringResource(id = R.string.edit_product_line)
                    )
                }
            }
        }

        DotBetweenTextUI(
            modifier = Modifier
                .padding(horizontal = Dimens.spaceDefault),
            start = stringResource(id = R.string.quantity_equals),
            end = creditProduct.quantity
        )

        DotBetweenTextUI(
            modifier = Modifier
                .padding(horizontal = Dimens.spaceDefault),
            start = stringResource(id = R.string.unit_price),
            end = stringResource(id = R.string.cordoba_symbol_x, creditProduct.productPrice)
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.spaceDefault)
                .padding(vertical = Dimens.spaceDefault),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.End,
            text = stringResource(id = R.string.cordoba_symbol_x, creditProduct.total)
        )

    }

}

@Composable
fun AddProductLineButton(
    onAddProductLinePressed: (() -> Unit)
) {

    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.space2x, vertical = Dimens.space2x),
        onClick = onAddProductLinePressed
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.add_product)
            )
            Spacer(modifier = Modifier.width(Dimens.spaceDefault))
            Text(
                text = stringResource(id = R.string.add_product).uppercase()
            )
        }
    }

}

@Composable
fun ActionHandler(
    navController: NavHostController,
    viewModel: CreditFormViewModel
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.action.collect { action ->
            when (action) {
                CreditFormAction.ShowCustomerSelector -> {
                    navController.navigate("customer_selector")
                }
                is CreditFormAction.ShowCreditProductForm -> {
                    navController.navigate(
                        "credit_product_form",
                        args = bundleOf(
                            creditProductNavKey to action.creditProduct
                        )
                    )
                }
                CreditFormAction.ReturnToCredits -> {
                    navController.navigateUp()
                }
            }
        }
    }

}

@Composable
fun NavigationBackResultsHandler(
    navController: NavHostController,
    viewModel: CreditFormViewModel
) {

    navController.CollectNavigationBackResult(
        key = customerNavResultKey,
        initialValue = CustomerParcelable(),
        resultCallback = { customerBackResult ->
            viewModel.perform(CreditFormEvent.SetCustomer(customerBackResult))
        }
    )

    navController.CollectNavigationBackResult(
        key = creditProductNavResultKey,
        initialValue = CreditProductParcelable(),
        resultCallback = { creditProductBackResult ->
            viewModel.perform(CreditFormEvent.AddOrReplaceCreditProduct(creditProductBackResult))
        }
    )

}

private val MOCKED_CREDITS_FOR_PREVIEW = CreditFormState(
    customerSelected = Customer(
        id = 1,
        name = "Leonel",
        nickname = "Profesor Leonel",
    ),
    productLines = listOf(
        CreditProduct(
            productName = "Tajada con queso",
            productPrice = "50.00",
            quantity = "1",
            total = "50.00"
        ),
        CreditProduct(
            productName = "Papas fritas",
            productPrice = "60.00",
            quantity = "2",
            total = "120.00"
        )
    ),
    totalCreditAmount = "170.00"
)