@file:OptIn(ExperimentalMaterial3Api::class)

package com.rafalesan.products.presentation.products_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rafalesan.credikiosko.core.commons.presentation.composables.ToastHandlerComposable
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens
import com.rafalesan.products.R
import com.rafalesan.products.data.repository.ProductRepository
import com.rafalesan.products.domain.entity.Product
import com.rafalesan.products.domain.usecase.GetProductsUseCase
import com.rafalesan.credikiosko.core.R as CoreR

@Preview
@Composable
fun ProductsPreview() {
    ProductsUI(
        navController = NavHostController(LocalContext.current),
        viewModel = ProductsViewModel(GetProductsUseCase(ProductRepository()))
    )
}

@Composable
fun ProductsScreen(
    navController: NavHostController,
    viewModel: ProductsViewModel = hiltViewModel()
) {
    ProductsUI(
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

        val productList: List<Product> by viewModel.productList.collectAsState()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = innerPadding
        ) {
            items(productList, key = { it.id }) { product ->
                ProductItem(product)
                Divider(color = Color.LightGray, thickness = 1.dp)
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
    )
}

@Composable
fun ProductItem(product: Product) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
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
