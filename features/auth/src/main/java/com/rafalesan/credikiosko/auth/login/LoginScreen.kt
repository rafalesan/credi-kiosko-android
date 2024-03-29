package com.rafalesan.credikiosko.auth.login

import android.provider.Settings
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rafalesan.credikiosko.auth.R
import com.rafalesan.credikiosko.core.commons.presentation.composables.AppLogo
import com.rafalesan.credikiosko.core.commons.presentation.composables.OutlinedPasswordFieldWithError
import com.rafalesan.credikiosko.core.commons.presentation.composables.OutlinedTextFieldWithError
import com.rafalesan.credikiosko.core.commons.presentation.composables.UiStateHandlerComposable
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens
import com.rafalesan.credikiosko.core.commons.presentation.theme.Teal200
import com.rafalesan.credikiosko.core.R as CoreR

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {

    LoginUI(viewModel = viewModel)

    ActionHandler(
        viewModel = viewModel,
        navController = navController
    )

}

@Composable
fun LoginUI(viewModel: LoginViewModel) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        constraintSet = screenConstraintSet
    ) {

        AppLogo(
            modifier = Modifier
                .layoutId(AppLogoTag)
                .padding(horizontal = Dimens.space8x)
                .padding(top = Dimens.space4x),
        )
        AppName()
        LoginDescription()
        EmailInput(viewModel)
        PasswordInput(viewModel)
        LoginButton(viewModel)
        CreateAccountButton(viewModel)

    }

    viewModel.deviceName = Settings.Global.getString(
        LocalContext.current.contentResolver,
        "device_name",
    )

    UiStateHandlerComposable(
        viewModel = viewModel
    )
}

@Composable
fun AppName() {
    Text(
        modifier = Modifier
            .layoutId(AppNameTag)
            .padding(horizontal = Dimens.space4x)
            .padding(bottom = Dimens.space2x),
        text = stringResource(id = CoreR.string.app_name),
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun LoginDescription() {
    Text(
        modifier = Modifier
            .layoutId(LoginDescriptionTag)
            .padding(bottom = Dimens.space3x),
        text = stringResource(id = CoreR.string.login_description),
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center
    )

}

@Composable
fun EmailInput(viewModel: LoginViewModel) {

    val emailError = viewModel.formErrors.collectAsState().value["email"]
    val emailText = viewModel.email.collectAsState()

    OutlinedTextFieldWithError(
        modifier = Modifier
            .layoutId(EmailTag)
            .padding(horizontal = Dimens.space2x)
            .padding(bottom = Dimens.space2x),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        value = emailText.value,
        errorStringId = emailError,
        onValueChange = { viewModel.email.value = it },
        label = { Text(text = stringResource(id = R.string.email)) },
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Email,
                contentDescription = stringResource(id = R.string.email)
            )
        }
    )

}

@Composable
fun PasswordInput(viewModel: LoginViewModel) {

    val passwordText = viewModel.password.collectAsState()
    val passwordError = viewModel.formErrors.collectAsState().value["password"]

    OutlinedPasswordFieldWithError(
        modifier = Modifier
            .layoutId(PasswordTag)
            .padding(horizontal = Dimens.space2x)
            .padding(bottom = Dimens.space3x),
        value = passwordText.value,
        errorStringId = passwordError,
        onValueChange = { viewModel.password.value = it },
        label = { Text(text = stringResource(id = R.string.password)) },
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Go
        ),
        keyboardActions = KeyboardActions(
            onGo = {
                viewModel.perform(LoginEvent.Login)
            }
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Lock,
                contentDescription = stringResource(id = R.string.lock_icon)
            )
        },
    )
}

@Composable
fun LoginButton(viewModel: LoginViewModel) {
    Button(
        modifier = Modifier
            .layoutId(LoginBtnTag)
            .padding(horizontal = Dimens.space2x)
            .padding(bottom = Dimens.space2x)
            .height(Dimens.space6x),
        onClick = {
            viewModel.perform(LoginEvent.Login)
        }
    ) {
        Text(text = stringResource(id = R.string.log_in).uppercase())
    }
}

@Composable
fun CreateAccountButton(viewModel: LoginViewModel) {
    Button(
        modifier = Modifier
            .layoutId(CreateAccountBtnTag)
            .padding(horizontal = Dimens.space2x)
            .padding(bottom = Dimens.space4x)
            .height(Dimens.space6x),
        colors = ButtonDefaults.buttonColors(containerColor = Teal200),
        onClick = {
            viewModel.perform(LoginEvent.CreateAccount)
        }
    ) {
        Text(text = stringResource(id = R.string.create_an_account).uppercase())
    }
}

@Composable
fun ActionHandler(
    viewModel: LoginViewModel,
    navController: NavHostController
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.action.collect { event ->
            when (event) {
                LoginAction.OpenHome -> navController.navigate("home")
                LoginAction.OpenSignup -> navController.navigate("sign_up")
            }
        }
    }
}
