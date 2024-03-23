@file:OptIn(ExperimentalMaterial3Api::class)

package com.rafalesan.credikiosko.products.presentation.product_selector

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
import com.rafalesan.credikiosko.core.commons.domain.entity.Product
import com.rafalesan.credikiosko.core.commons.presentation.composables.ToastHandlerComposable
import com.rafalesan.credikiosko.core.commons.presentation.extensions.popBackStackWithResult
import com.rafalesan.credikiosko.core.commons.presentation.theme.CrediKioskoTheme
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens
import com.rafalesan.credikiosko.core.commons.productNavResultKey
import com.rafalesan.credikiosko.products.R
import com.rafalesan.credikiosko.products.presentation.composables.ProductItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ProductSelectorScreen(
    navController: NavHostController,
    viewModel: ProductSelectorViewModel = hiltViewModel()
) {

    ProductSelectorUI(
        productsPagingListFlow = viewModel.productList,
        onBackPressed = { navController.navigateUp() },
        onProductPressed = { viewModel.perform(ProductSelectorEvent.ProductSelected(it)) }
    )

    ActionHandler(
        navController,
        viewModel
    )

    ToastHandlerComposable(viewModel = viewModel)

}

@Preview
@Composable
fun ProductSelectorUIPreview() {
    CrediKioskoTheme {
        ProductSelectorUI(
            productsPagingListFlow = MutableStateFlow(
                PagingData.from(MOCKED_PRODUCTS_FOR_PREVIEW)
            )
        )
    }
}

@Composable
fun ProductSelectorUI(
    productsPagingListFlow: StateFlow<PagingData<Product>>,
    onBackPressed: () -> Unit = {},
    onProductPressed: (Product) -> Unit = {}
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
                    Text(stringResource(id = R.string.select_the_product))
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Filled.ArrowBack, stringResource(id = com.rafalesan.credikiosko.core.R.string.navigate_back))
                    }
                }
            )
        },
    ) { innerPadding ->

        val productPagingList: LazyPagingItems<Product> = productsPagingListFlow.collectAsLazyPagingItems()
        val isEmptyProductList by remember {
            derivedStateOf { productPagingList.itemCount == 0 }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()),
            contentPadding = PaddingValues(bottom = Dimens.space10x)
        ) {

            items(productPagingList.itemCount) { index ->
                ProductItem(
                    productPagingList[index]!!,
                    onClick = { onProductPressed(it) }
                )
                Divider(color = Color.LightGray, thickness = 1.dp)
            }


            if (isEmptyProductList) {
                item {
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = stringResource(id = R.string.empty_product_list_desc)
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
    viewModel: ProductSelectorViewModel
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.action.collect { action ->
            when (action) {
                is ProductSelectorAction.ReturnProduct -> {
                    navController.popBackStackWithResult(
                        productNavResultKey,
                        action.product
                    )
                }
            }
        }
    }
}

val MOCKED_PRODUCTS_FOR_PREVIEW = listOf(
    Product(
        1,
        "Taza de café",
        "7.00"
    ),
    Product(
        2,
        "Tajada con queso",
        "30.00"
    ),
    Product(
        3,
        "Papitas fritas",
        "60.00"
    )
)