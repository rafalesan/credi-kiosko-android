package com.rafalesan.credikiosko.auth.signup

import android.provider.Settings
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.rafalesan.credikiosko.core.R
import com.rafalesan.credikiosko.core.commons.presentation.composables.OutlinedPasswordFieldWithError
import com.rafalesan.credikiosko.core.commons.presentation.composables.OutlinedTextFieldWithError
import com.rafalesan.credikiosko.core.commons.presentation.composables.ToastHandlerComposable
import com.rafalesan.credikiosko.core.commons.presentation.composables.UiStateHandlerComposable
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens

@Deprecated("It should be used SignUpScreenNavCompose instead")
@Composable
fun SignUpScreen(viewModel: SignupViewModel) {

    SignUpUi(viewModel = viewModel)

}

@Composable
fun SignUpScreenNavCompose(
    viewModel: SignupViewModel = hiltViewModel()
) {
    SignUpUi(viewModel = viewModel)
}

@Composable
fun SignUpUi(
    viewModel: SignupViewModel
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        constraintSet = signUpConstraintSet
    ) {
        AppLogo()
        AppName()
        SignUpDescription()
        CompleteNameInput(viewModel = viewModel)
        UserNameInput(viewModel = viewModel)
        BusinessNameInput(viewModel = viewModel)
        EmailInput(viewModel = viewModel)
        PasswordInput(viewModel = viewModel)
        PasswordConfirmationInput(viewModel = viewModel)
        RegisterButton(viewModel = viewModel)
    }

    viewModel.deviceName = Settings.Global.getString(
        LocalContext.current.contentResolver,
        "device_name",
    )

    UiStateHandlerComposable(
        viewModel = viewModel
    )

    ToastHandlerComposable(viewModel = viewModel)
}

@Composable
fun AppLogo() {
    Image(
        modifier = Modifier
            .layoutId(SignUpViewTag.AppLogo)
            .width(Dimens.signUpAppLogoSize)
            .padding(top = Dimens.space2x),
        painter = painterResource(id = R.drawable.ic_app),
        contentDescription = stringResource(id = R.string.app_name)
    )
}

@Composable
fun AppName() {
    Text(
        modifier = Modifier
            .layoutId(SignUpViewTag.AppName)
            .padding(horizontal = Dimens.space4x)
            .padding(bottom = Dimens.space2x),
        text = stringResource(id = R.string.app_name),
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun SignUpDescription() {
    Text(
        modifier = Modifier
            .layoutId(SignUpViewTag.SignUpDescription)
            .padding(bottom = Dimens.space2x),
        text = stringResource(id = R.string.signup_description),
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center
    )
}

@Composable
fun CompleteNameInput(viewModel: SignupViewModel) {
    val completeNameText = viewModel.username.collectAsState()
    val completeNameError = viewModel.formErrors.collectAsState().value["name"]

    BaseSignUpInput(
        text = completeNameText,
        errorId = completeNameError,
        signUpViewTag = SignUpViewTag.CompleteName,
        onValueChange = { viewModel.username.value = it },
        labelStringRes = R.string.complete_name,
        leadingIconImageVector = Icons.Outlined.Person
    )
}

@Composable
fun UserNameInput(viewModel: SignupViewModel) {
    val userNameText = viewModel.nickname.collectAsState()
    val userNameError = viewModel.formErrors.collectAsState().value["nickname"]

    BaseSignUpInput(
        text = userNameText,
        errorId = userNameError,
        signUpViewTag = SignUpViewTag.UserName,
        onValueChange = { viewModel.nickname.value = it },
        labelStringRes = R.string.nickname,
        leadingIconImageVector = Icons.Outlined.PersonOutline
    )
}

@Composable
fun BusinessNameInput(viewModel: SignupViewModel) {
    val businessNameText = viewModel.businessName.collectAsState()
    val businessNameError = viewModel.formErrors.collectAsState().value["businessName"]

    BaseSignUpInput(
        text = businessNameText,
        errorId = businessNameError,
        signUpViewTag = SignUpViewTag.BusinessName,
        onValueChange = { viewModel.businessName.value = it },
        labelStringRes = R.string.business_name,
        leadingIconImageVector = Icons.Outlined.Store
    )

}

@Composable
fun EmailInput(viewModel: SignupViewModel) {
    val emailText = viewModel.email.collectAsState()
    val emailError = viewModel.formErrors.collectAsState().value["email"]

    BaseSignUpInput(
        text = emailText,
        errorId = emailError,
        signUpViewTag = SignUpViewTag.Email,
        onValueChange = { viewModel.email.value = it },
        labelStringRes = R.string.email,
        leadingIconImageVector = Icons.Outlined.Email,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
    )

}

@Composable
fun PasswordInput(viewModel: SignupViewModel) {
    val passwordText = viewModel.password.collectAsState()
    val passwordError = viewModel.formErrors.collectAsState().value["password"]

    BaseSignUpPasswordInput(
        text = passwordText,
        errorId = passwordError,
        signUpViewTag = SignUpViewTag.Password,
        onValueChange = { viewModel.password.value = it },
        labelStringRes = R.string.password
    )
}

@Composable
fun PasswordConfirmationInput(viewModel: SignupViewModel) {
    val passwordConfirmationText = viewModel.passwordConfirmation.collectAsState()
    val passwordConfirmationError = viewModel.formErrors.collectAsState().value["passwordConfirmation"]

    BaseSignUpPasswordInput(
        text = passwordConfirmationText,
        errorId = passwordConfirmationError,
        signUpViewTag = SignUpViewTag.PasswordConfirmation,
        onValueChange = { viewModel.passwordConfirmation.value = it },
        labelStringRes = R.string.repeat_password
    )
}

@Composable
fun RegisterButton(viewModel: SignupViewModel) {
    Button(
        modifier = Modifier
            .layoutId(SignUpViewTag.RegisterButton)
            .padding(horizontal = Dimens.space2x)
            .padding(bottom = Dimens.space4x)
            .height(Dimens.space6x),
        onClick = {
            viewModel.perform(SignupAction.Signup)
        }
    ) {
        Text(text = stringResource(id = R.string.sign_up).uppercase())
    }
}

@Composable
fun BaseSignUpPasswordInput(
    text: State<String>,
    errorId: Int?,
    signUpViewTag: SignUpViewTag,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Password,
        imeAction = ImeAction.Next
    ),
    onValueChange: ((String) -> Unit),
    @StringRes labelStringRes: Int,
    leadingIconImageVector: ImageVector = Icons.Outlined.Lock
) {

    OutlinedPasswordFieldWithError(
        modifier = Modifier
            .layoutId(signUpViewTag)
            .padding(horizontal = Dimens.space2x)
            .padding(bottom = Dimens.space2x),
        keyboardOptions = keyboardOptions,
        value = text.value,
        errorStringId = errorId,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(id = labelStringRes)) },
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = leadingIconImageVector,
                contentDescription = stringResource(id = labelStringRes)
            )
        }
    )

}

@Composable
fun BaseSignUpInput(
    text: State<String>,
    errorId: Int?,
    signUpViewTag: SignUpViewTag,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next
    ),
    onValueChange: ((String) -> Unit),
    @StringRes labelStringRes: Int,
    leadingIconImageVector: ImageVector
) {

    OutlinedTextFieldWithError(
        modifier = Modifier
            .layoutId(signUpViewTag)
            .padding(horizontal = Dimens.space2x)
            .padding(bottom = Dimens.space2x),
        keyboardOptions = keyboardOptions,
        value = text.value,
        errorStringId = errorId,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(id = labelStringRes)) },
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = leadingIconImageVector,
                contentDescription = stringResource(id = labelStringRes)
            )
        }
    )

}
