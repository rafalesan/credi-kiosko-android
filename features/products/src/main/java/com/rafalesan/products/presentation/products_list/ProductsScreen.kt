@file:OptIn(ExperimentalMaterial3Api::class)

package com.rafalesan.products.presentation.products_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.rafalesan.credikiosko.core.commons.presentation.composables.ToastHandlerComposable
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens
import com.rafalesan.products.R
import com.rafalesan.products.domain.entity.Product
import com.rafalesan.credikiosko.core.R as CoreR

@Composable
fun ProductsScreen(
    navController: NavHostController,
    viewModel: ProductsViewModel = hiltViewModel()
) {
    ProductsUI(
        viewModel = viewModel,
        navController = navController
    )
    ActionHandler(
        viewModel = viewModel,
        navController = navController
    )
    ToastHandlerComposable(viewModel)
}

@Composable
fun ProductsUI(
    navController: NavHostController,
    viewModel: ProductsViewModel
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
                    Text(stringResource(id = R.string.products))
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Filled.ArrowBack, stringResource(id = CoreR.string.navigate_back))
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.perform(ProductsEvent.CreateNewProduct) },
                icon = { Icon(Icons.Filled.Add, stringResource(id = R.string.create_new_product)) },
                text = { Text(stringResource(id = R.string.new_product))},
                shape = RoundedCornerShape(16.dp),
            )
        }
    ) { innerPadding ->

        val productPagingList: LazyPagingItems<Product> = viewModel.productList.collectAsLazyPagingItems()
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
                    onClick = { viewModel.perform(ProductsEvent.ShowProduct(it)) }
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

            with (productPagingList) {

                when {
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier.fillParentMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .width(64.dp)
                                        .align(Alignment.Center),
                                    color = MaterialTheme.colorScheme.secondary,
                                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                                )
                            }
                        }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val error = loadState.refresh as LoadState.Error
                        item {
                            Text(
                                modifier = Modifier.fillMaxSize(),
                                textAlign = TextAlign.Center,
                                text = error.error.localizedMessage
                                    ?: stringResource(id = CoreR.string.unknown_error_description)
                            )
                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = Dimens.space2x),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.secondary,
                                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                                )
                            }
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        val error = loadState.append as LoadState.Error
                        item {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = Dimens.space2x),
                                textAlign = TextAlign.Center,
                                text = error.error.localizedMessage
                                    ?: stringResource(id = CoreR.string.unknown_error_description)
                            )
                        }
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductItemPreview() {
    ProductItem(
        product = Product(
            1,
            "Taza de café",
            "7"
        )
    ) {}
}

@Composable
fun ProductItem(
    product: Product,
    onClick: (Product) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(product) }
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = Dimens.space2x)
                .padding(top = Dimens.space2x),
            text = product.name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .padding(horizontal = Dimens.space2x)
                .padding(top = Dimens.spaceDefault, bottom = Dimens.space2x),
            text = stringResource(
                id = R.string.price_x,
                product.price
            )
        )
    }
}

@Composable
fun ActionHandler(
    viewModel: ProductsViewModel,
    navController: NavHostController
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.action.collect { action ->
            when(action) {
                is ProductsAction.ShowProductForm -> {
                    var route = "product_form"

                    action.product?.let {
                        route += "?product_id=${it.id}" +
                            "?product_name=${it.name}" +
                            "?product_price=${it.price}"
                    }

                    navController.navigate(route)
                }
            }
        }
    }
}
