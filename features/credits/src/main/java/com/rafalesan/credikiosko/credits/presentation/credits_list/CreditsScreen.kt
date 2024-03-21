@file:OptIn(ExperimentalMaterial3Api::class)

package com.rafalesan.credikiosko.credits.presentation.credits_list

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
import com.rafalesan.credikiosko.core.commons.presentation.utils.DateFormatUtil
import com.rafalesan.credikiosko.credits.R
import com.rafalesan.credikiosko.credits.domain.entity.Credit
import com.rafalesan.credikiosko.credits.domain.entity.CreditWithCustomerAndProducts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.rafalesan.credikiosko.core.R as CoreR

@Composable
fun CreditsScreen(
    navController: NavHostController,
    viewModel: CreditsViewModel = hiltViewModel()
) {

    CreditsUI(
        creditsPagingListFlow = viewModel.creditList,
        onBackPressed = { navController.navigateUp() },
        onNewCreditPressed = { viewModel.perform(CreditsEvent.CreateNewCredit) },
        onCreditPressed = { viewModel.perform(CreditsEvent.ShowCredit(it)) }
    )

    ActionHandler(
        navController = navController,
        viewModel = viewModel
    )

    ToastHandlerComposable(viewModel = viewModel)

}

@Preview
@Composable
fun CreditsUIPreview() {
    CreditsUI(
        creditsPagingListFlow = getPreviewMockCreditsFlow(),
    )
}

@Composable
fun CreditsUI(
    creditsPagingListFlow: StateFlow<PagingData<CreditWithCustomerAndProducts>>,
    onBackPressed: (() -> Unit) = {},
    onNewCreditPressed: (() -> Unit) = {},
    onCreditPressed: ((Credit) -> Unit) = {}
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
                    Text(stringResource(id = CoreR.string.credits))
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
                onClick = onNewCreditPressed,
                icon = { Icon(Icons.Filled.Add, stringResource(id = R.string.create_new_credit)) },
                text = { Text(stringResource(id = R.string.new_credit)) },
                shape = RoundedCornerShape(16.dp),
            )
        }
    ) { innerPadding ->

        val creditPagingList: LazyPagingItems<CreditWithCustomerAndProducts> =
            creditsPagingListFlow.collectAsLazyPagingItems()

        val isEmptyCreditList by remember {
            derivedStateOf { creditPagingList.itemCount == 0 }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()),
            contentPadding = PaddingValues(bottom = Dimens.space10x)
        ) {

            items(creditPagingList.itemCount) { index ->
                CreditItem(
                    creditPagingList[index]!!,
                    onItemPressed = onCreditPressed
                )
                Divider(color = Color.LightGray, thickness = 1.dp)
            }

            if (isEmptyCreditList) {
                item {
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = stringResource(id = R.string.empty_credit_list_desc)
                        )
                    }
                }
            }

        }

    }

}

@Composable
fun CreditItem(
    creditWithCustomerAndProducts: CreditWithCustomerAndProducts,
    onItemPressed: (Credit) -> Unit
) {
    val (credit, customer) = creditWithCustomerAndProducts

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemPressed(credit) }
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = Dimens.space2x)
                .padding(top = Dimens.space2x),
            text = customer.name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        val creditDate = DateFormatUtil.getUIDateFormatFrom(credit.date)
        val creditTime = DateFormatUtil.getUITimeFormatFrom(credit.date)
        Text(
            modifier = Modifier
                .padding(horizontal = Dimens.space2x),
            text = stringResource(id = R.string.date_x, creditDate),
        )
        Text(
            modifier = Modifier
                .padding(horizontal = Dimens.space2x),
            text = stringResource(id = R.string.time_x, creditTime),
        )
        Text(
            modifier = Modifier
                .padding(horizontal = Dimens.space2x)
                .padding(bottom = Dimens.space2x),
            text = stringResource(id = R.string.total_x, credit.total),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }

}

@Composable
fun ActionHandler(
    navController: NavHostController,
    viewModel: CreditsViewModel
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.action.collect { action ->
            when (action) {
                CreditsAction.ShowCreditForm -> {
                    navController.navigate("credit_form")
                }
            }
        }
    }

}

private fun getPreviewMockCreditsFlow(): StateFlow<PagingData<CreditWithCustomerAndProducts>> {
    return MutableStateFlow(
        PagingData.from(
            listOf(
                CreditWithCustomerAndProducts(
                    Credit(0, 0, 0, "2023-12-26 23:04:06", "161.00"),
                    Customer(1, "Rafael Antonio Alegría Sánchez", ""),
                    listOf()
                ),
                CreditWithCustomerAndProducts(
                    Credit(1, 0, 0, "2023-12-26 23:04:06", "161.00"),
                    Customer(1, "Rafael Antonio Alegría Sánchez", ""),
                    listOf()
                ),
                CreditWithCustomerAndProducts(
                    Credit(2, 0, 0, "2023-12-26 23:04:06", "161.00"),
                    Customer(1, "Rafael Antonio Alegría Sánchez", ""),
                    listOf()
                )
            )
        )
    )
}
