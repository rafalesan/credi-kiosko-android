@file:OptIn(ExperimentalMaterial3Api::class)

package com.rafalesan.credikiosko.customers.presentation.customer_form

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rafalesan.credikiosko.core.commons.presentation.composables.DeleteButton
import com.rafalesan.credikiosko.core.commons.presentation.composables.OutlinedTextFieldWithError
import com.rafalesan.credikiosko.core.commons.presentation.composables.ToastHandlerComposable
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens
import com.rafalesan.credikiosko.customers.R
import com.rafalesan.credikiosko.core.R as CoreR

@Composable
fun CustomerFormScreen(
    navController: NavHostController,
    viewModel: CustomerFormViewModel = hiltViewModel()
) {
    CustomerUI(
        viewState = viewModel.viewState.collectAsState(),
        onBackPressed = { navController.navigateUp() },
        onSaveCustomerPressed = { viewModel.perform(CustomerFormEvent.SaveCustomer) },
        onDeleteProductPressed = { viewModel.perform(CustomerFormEvent.DeleteCustomer) },
        onCustomerNameChanged = { viewModel.perform(CustomerFormEvent.SetCustomerName(it)) },
        onCustomerNicknameChanged = { viewModel.perform(CustomerFormEvent.SetCustomerNickname(it)) },
        onCustomerEmailChanged = { viewModel.perform(CustomerFormEvent.SetCustomerEmail(it)) }
    )

    ActionHandler(
        viewModel = viewModel,
        navController = navController
    )

    ToastHandlerComposable(viewModel)

}

@Preview
@Composable
fun CustomerUIPreview() {
    CustomerUI(
        viewState = remember { mutableStateOf(CustomerFormState()) }
    )
}

@Composable
fun CustomerUI(
    viewState: State<CustomerFormState>,
    onBackPressed: (() -> Unit) = {},
    onSaveCustomerPressed: (() -> Unit) = {},
    onDeleteProductPressed: (() -> Unit) = {},
    onCustomerNameChanged: ((String) -> Unit) = {},
    onCustomerNicknameChanged: ((String) -> Unit) = {},
    onCustomerEmailChanged: ((String) -> Unit) = {}
) {
    Scaffold(
        topBar = {

            val titleText by remember {
                derivedStateOf {
                    if (viewState.value.isNewCustomer) {
                        R.string.new_customer
                    } else {
                        R.string.modify_customer
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

                        if (!viewState.value.isNewCustomer) {
                            Text(
                                stringResource(
                                    R.string.editing_customer_x,
                                    viewState.value.customerName
                                ),
                                style = MaterialTheme.typography.titleSmall
                            )
                        }

                    }
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
                onClick = onSaveCustomerPressed,
                icon = { Icon(Icons.Filled.Save, stringResource(id = R.string.save_customer)) },
                text = { Text(stringResource(id = R.string.save_customer)) },
                shape = RoundedCornerShape(16.dp),
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            CustomerNameInput(
                viewState = viewState,
                onCustomerNameChanged = onCustomerNameChanged
            )

            CustomerNicknameInput(
                viewState = viewState,
                onCustomerNicknameChanged = onCustomerNicknameChanged
            )

            CustomerEmailInput(
                viewState = viewState,
                onCustomerEmailChanged = onCustomerEmailChanged
            )

            if (!viewState.value.isNewCustomer) {
                DeleteButton(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = Dimens.space2x),
                    onDeleteProductPressed
                )
            }

        }

    }
}

@Composable
fun CustomerNameInput(
    viewState: State<CustomerFormState>,
    onCustomerNameChanged: (String) -> Unit
) {

    val customerNameText by remember {
        derivedStateOf {
            viewState.value.customerName
        }
    }
    val customerNameError by remember {
        derivedStateOf {
            viewState.value.customerNameError
        }
    }

    OutlinedTextFieldWithError(
        modifier = Modifier
            .padding(horizontal = Dimens.space2x)
            .padding(top = Dimens.space2x),
        value = customerNameText,
        label = { Text(text = stringResource(id = R.string.customer_name)) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        errorStringId = customerNameError,
        onValueChange = onCustomerNameChanged
    )

}

@Composable
fun CustomerNicknameInput(
    viewState: State<CustomerFormState>,
    onCustomerNicknameChanged: (String) -> Unit
) {

    val customerNicknameText by remember {
        derivedStateOf {
            viewState.value.customerNickname
        }
    }

    OutlinedTextFieldWithError(
        modifier = Modifier
            .padding(horizontal = Dimens.space2x)
            .padding(top = Dimens.space2x),
        value = customerNicknameText,
        label = { Text(text = stringResource(id = R.string.alias_or_nickname)) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        onValueChange = onCustomerNicknameChanged
    )

}

@Composable
fun CustomerEmailInput(
    viewState: State<CustomerFormState>,
    onCustomerEmailChanged: (String) -> Unit
) {

    val customerEmailText by remember {
        derivedStateOf {
            viewState.value.customerEmail
        }
    }

    val customerEmailError by remember {
        derivedStateOf {
            viewState.value.customerEmailError
        }
    }

    OutlinedTextFieldWithError(
        modifier = Modifier
            .padding(horizontal = Dimens.space2x)
            .padding(top = Dimens.space2x),
        value = customerEmailText,
        label = { Text(text = stringResource(id = CoreR.string.email)) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Email
        ),
        errorStringId = customerEmailError,
        onValueChange = onCustomerEmailChanged
    )

}

@Composable
fun ActionHandler(
    viewModel: CustomerFormViewModel,
    navController: NavHostController
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.action.collect { action ->
            when (action) {
                CustomerFormAction.ReturnToCustomerList -> {
                    navController.navigateUp()
                }
            }
        }
    }

}
