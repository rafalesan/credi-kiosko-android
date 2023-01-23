package com.rafalesan.credikiosko.auth.login

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.rafalesan.credikiosko.auth.R
import com.rafalesan.credikiosko.auth.mainActivityNameSpace
import com.rafalesan.credikiosko.core.commons.presentation.composables.OutlinedPasswordFieldWithError
import com.rafalesan.credikiosko.core.commons.presentation.composables.OutlinedTextFieldWithError
import com.rafalesan.credikiosko.core.commons.presentation.composables.UiStateHandlerComposable
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens
import com.rafalesan.credikiosko.core.commons.presentation.theme.Teal200
import com.rafalesan.credikiosko.core.R as CoreR

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        constraintSet = screenConstraintSet
    ) {

        AppLogo()
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

    EventHandler(
        viewModel = viewModel,
        navController = navController
    )

}

@Composable
fun AppLogo() {
    Image(
        modifier = Modifier
            .layoutId(AppLogoTag)
            .padding(horizontal = Dimens.space8x)
            .padding(top = Dimens.space4x),
        painter = painterResource(id = CoreR.drawable.ic_app),
        contentDescription = stringResource(id = CoreR.string.app_name)
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
        color = MaterialTheme.colors.onSurface,
        style = MaterialTheme.typography.h5,
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
        color = MaterialTheme.colors.onSurface,
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
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
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
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Go
        ),
        keyboardActions = KeyboardActions(
            onGo = {
                viewModel.perform(LoginAction.Login)
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
            viewModel.perform(LoginAction.Login)
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
        colors = ButtonDefaults.buttonColors(backgroundColor = Teal200),
        onClick = {
            viewModel.perform(LoginAction.CreateAccount)
        }
    ) {
        Text(text = stringResource(id = R.string.create_an_account).uppercase())
    }
}

@Composable
fun EventHandler(
    viewModel: LoginViewModel,
    navController: NavController
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.event.collect { event ->
            when(event) {
                LoginEvent.OpenHome -> openHome(context)
                LoginEvent.OpenSignup -> openSignUp(navController)
            }
        }
    }
}

private fun openHome(context: Context) {
    val classForName = Class.forName(mainActivityNameSpace)
    val intent = Intent(context, classForName)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
            Intent.FLAG_ACTIVITY_CLEAR_TASK or
            Intent.FLAG_ACTIVITY_CLEAR_TOP
    context.startActivity(intent)
}

private fun openSignUp(navController: NavController) {
    navController.navigate(R.id.action_to_signup_fragment)
}
