package com.rafalesan.credikiosko.onboarding.presentation

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Store
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rafalesan.credikiosko.core.commons.presentation.composables.AppLogo
import com.rafalesan.credikiosko.core.commons.presentation.composables.OutlinedTextFieldWithError
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens
import com.rafalesan.credikiosko.core.commons.presentation.theme.Teal200
import com.rafalesan.credikiosko.onboarding.R

@Composable
fun WelcomeScreen(
    navController: NavHostController,
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    WelcomeUI()
}

@Preview
@Composable
fun WelcomeUIPreview() {
    WelcomeUI()
}

@Composable
fun WelcomeUI() {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            constraintSet = screenConstraintSet
        ) {
            AppLogo(
                modifier = Modifier
                    .layoutId(AppLogoTag)
            )
            Text(
                modifier = Modifier
                    .layoutId(WelcomeTitleTag)
                    .padding(horizontal = Dimens.space2x)
                    .padding(top = Dimens.space2x),
                text = stringResource(id = R.string.welcome_to_credikiosko),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .layoutId(WelcomeDescriptionTag)
                    .padding(horizontal = Dimens.space3x)
                    .padding(top = Dimens.space2x),
                text = stringResource(id = R.string.welcome_description),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            BusinessNameInput()
            ContinueButton()
        }
    }

}

@Composable
fun BusinessNameInput() {
    val businessNameText = remember {
        mutableStateOf("")
    }
    val businessNameError = remember {
        mutableStateOf(null)
    }

    OutlinedTextFieldWithError(
        modifier = Modifier
            .layoutId(BusinessNameInputTag)
            .padding(horizontal = Dimens.space2x)
            .padding(top = Dimens.space2x),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        value = businessNameText.value,
        errorStringId = businessNameError.value,
        onValueChange = { businessNameText.value = it },
        label = { Text(text = stringResource(id = R.string.kiosk_name)) },
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Store,
                contentDescription = stringResource(id = R.string.kiosk)
            )
        }
    )
}

@Composable
fun ContinueButton() {
    Button(
        modifier = Modifier
            .layoutId(ContinueButtonTag)
            .padding(horizontal = Dimens.space2x)
            .padding(top = Dimens.space4x, bottom = Dimens.space4x)
            .height(Dimens.space6x),
        colors = ButtonDefaults.buttonColors(containerColor = Teal200),
        onClick = {
            //TODO
        }
    ) {
        Text(text = stringResource(id = R.string.continue_text).uppercase())
    }
}
