@file:OptIn(ExperimentalMaterial3Api::class)

package com.rafalesan.credikiosko.customers.presentation.customers_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.rafalesan.credikiosko.core.commons.domain.entity.Customer
import com.rafalesan.credikiosko.core.commons.presentation.composables.ToastHandlerComposable
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens
import com.rafalesan.credikiosko.customers.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import com.rafalesan.credikiosko.core.R as CoreR

@Composable
fun CustomersScreen(
    navController: NavHostController,
    viewModel: CustomersViewModel = hiltViewModel()
) {

    CustomersUI(
        customersPagingListFlow = viewModel.customerList,
        onBackPressed = { navController.navigateUp() },
        onNewCustomerPressed = { viewModel.perform(CustomersEvent.CreateNewCustomer) },
        onCustomerPressed = { viewModel.perform(CustomersEvent.ShowCustomer(it)) }
    )

    ToastHandlerComposable(viewModel = viewModel)

    ActionHandler(
        viewModel,
        navController
    )

}

@Preview
@Composable
fun CustomersUIPreview() {
    CustomersUI(
        customersPagingListFlow = getMockCustomersFlow()
    )
}

@Composable
fun CustomersUI(
    customersPagingListFlow: Flow<PagingData<Customer>>,
    onBackPressed: (() -> Unit) = {},
    onNewCustomerPressed: (() -> Unit) = {},
    onCustomerPressed: ((Customer) -> Unit) = {}
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
                    Text(stringResource(id = R.string.customers))
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Filled.ArrowBack, stringResource(id = CoreR.string.navigate_back))
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNewCustomerPressed,
                icon = { Icon(Icons.Filled.Add, stringResource(id = R.string.create_new_customer)) },
                text = { Text(stringResource(id = R.string.new_customer)) },
                shape = RoundedCornerShape(16.dp),
            )
        }
    ) { innerPadding ->

        val customerPagingList: LazyPagingItems<Customer> =
            customersPagingListFlow.collectAsLazyPagingItems()

        val isEmptyCustomerList by remember {
            derivedStateOf{ customerPagingList.itemCount == 0 }
        }

        LazyColumn(
           modifier = Modifier
               .fillMaxSize()
               .padding(top = innerPadding.calculateTopPadding()),
            contentPadding = PaddingValues(bottom = Dimens.space10x)
        ) {

            items(customerPagingList.itemCount) { index ->
                CustomerItem(
                    customerPagingList[index]!!,
                    onItemPressed = onCustomerPressed
                )
                Divider(color = Color.LightGray, thickness = 1.dp)
            }

            if (isEmptyCustomerList) {
                item {
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = stringResource(id = R.string.empty_customer_list_desc)
                        )
                    }
                }
            }

        }

    }

}

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

@Composable
fun ActionHandler(
    viewModel: CustomersViewModel,
    navController: NavHostController
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.action.collect { action ->
            when (action) {
                is CustomersAction.ShowCustomerForm -> {
                    var route = "customer_form"

                    action.customer?.let {
                        route += "?customer_id=${it.id}" +
                            "?customer_name=${it.name}" +
                            "?customer_nickname=${it.nickname}" +
                            "?customer_email=${it.email}"
                    }

                    navController.navigate(route)
                }
            }
        }
    }
}

private fun getMockCustomersFlow(): Flow<PagingData<Customer>> {
    return flowOf(
        PagingData.from(
            listOf(
                Customer(1, "Rafael Antonio Alegría Sánchez", ""),
                Customer(2, "Gloria María Sánchez Muñoz", "Doña Gloria"),
                Customer(3, "Darling Lorena Alegría Sánchez", "Tierna")
            )
        )
    )
}