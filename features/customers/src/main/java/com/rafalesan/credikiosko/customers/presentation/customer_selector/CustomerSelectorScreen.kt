@file:OptIn(ExperimentalMaterial3Api::class)

package com.rafalesan.credikiosko.customers.presentation.customer_selector

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.rafalesan.credikiosko.core.commons.customerNavResultKey
import com.rafalesan.credikiosko.core.commons.domain.entity.Customer
import com.rafalesan.credikiosko.core.commons.presentation.composables.ToastHandlerComposable
import com.rafalesan.credikiosko.core.commons.presentation.extensions.popBackStackWithResult
import com.rafalesan.credikiosko.core.commons.presentation.theme.CrediKioskoTheme
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens
import com.rafalesan.credikiosko.customers.R
import com.rafalesan.credikiosko.customers.presentation.composables.CustomerItem
import com.rafalesan.credikiosko.customers.presentation.customers_list.getMockCustomersFlow
import kotlinx.coroutines.flow.Flow

@Composable
fun CustomerSelectorScreen(
    navHostController: NavHostController,
    viewModel: CustomerSelectorViewModel = hiltViewModel()
) {

    CustomerSelectorUI(
        customersPagingListFlow = viewModel.customerList,
        onBackPressed = { navHostController.navigateUp() },
        onCustomerPressed = { viewModel.perform(CustomerSelectorEvent.CustomerSelected(it)) }
    )

    ActionHandler(
        navHostController,
        viewModel
    )

    ToastHandlerComposable(viewModel = viewModel)

}

@Preview
@Composable
fun CustomerSelectorUIPreview() {
    CrediKioskoTheme {
        CustomerSelectorUI(
            customersPagingListFlow = getMockCustomersFlow()
        )
    }
}

@Composable
fun CustomerSelectorUI(
    customersPagingListFlow: Flow<PagingData<Customer>>,
    onBackPressed: () -> Unit = {},
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
                    Text(stringResource(id = R.string.select_the_customer))
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            stringResource(id = com.rafalesan.credikiosko.core.R.string.navigate_back)
                        )
                    }
                }
            )
        },
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
fun ActionHandler(
    navController: NavHostController,
    viewModel: CustomerSelectorViewModel,
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.action.collect { action ->
            when (action) {
                is CustomerSelectorAction.ReturnCustomer -> {
                    navController.popBackStackWithResult(
                        customerNavResultKey,
                        action.customer
                    )
                }
            }
        }
    }

}