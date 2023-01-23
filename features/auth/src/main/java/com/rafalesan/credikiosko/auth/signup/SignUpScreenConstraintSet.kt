package com.rafalesan.credikiosko.auth.signup

import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

sealed class SignUpViewTag {
    object AppLogo : SignUpViewTag()
    object AppName : SignUpViewTag()
    object SignUpDescription : SignUpViewTag()
    object CompleteName : SignUpViewTag()
    object UserName : SignUpViewTag()
    object BusinessName : SignUpViewTag()
    object Email : SignUpViewTag()
    object Password : SignUpViewTag()
    object PasswordConfirmation : SignUpViewTag()
    object RegisterButton : SignUpViewTag()
}

internal val signUpConstraintSet = ConstraintSet {
    val appLogo = createRefFor(SignUpViewTag.AppLogo)
    val appName = createRefFor(SignUpViewTag.AppName)
    val signUpDescription = createRefFor(SignUpViewTag.SignUpDescription)
    val completeName = createRefFor(SignUpViewTag.CompleteName)
    val userName = createRefFor(SignUpViewTag.UserName)
    val businessName = createRefFor(SignUpViewTag.BusinessName)
    val email = createRefFor(SignUpViewTag.Email)
    val password = createRefFor(SignUpViewTag.Password)
    val passwordConfirmation = createRefFor(SignUpViewTag.PasswordConfirmation)
    val registerButton = createRefFor(SignUpViewTag.RegisterButton)

    createVerticalChain(
        appLogo,
        appName,
        signUpDescription,
        completeName,
        userName,
        businessName,
        email,
        password,
        passwordConfirmation,
        registerButton,
        chainStyle = ChainStyle.Packed
    )

    constrain(appLogo) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(parent.top)
        bottom.linkTo(appName.top)
    }

    constrain(appName) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(appLogo.bottom)
        bottom.linkTo(signUpDescription.top)
    }

    constrain(signUpDescription) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(appName.bottom)
        bottom.linkTo(completeName.top)
    }

    constrain(completeName) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(signUpDescription.bottom)
        bottom.linkTo(userName.top)
        width = Dimension.fillToConstraints
    }

    constrain(userName) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(completeName.bottom)
        bottom.linkTo(businessName.top)
        width = Dimension.fillToConstraints
    }

    constrain(businessName) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(userName.bottom)
        bottom.linkTo(email.top)
        width = Dimension.fillToConstraints
    }

    constrain(email) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(businessName.bottom)
        bottom.linkTo(password.top)
        width = Dimension.fillToConstraints
    }

    constrain(password) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(email.bottom)
        bottom.linkTo(passwordConfirmation.top)
        width = Dimension.fillToConstraints
    }

    constrain(passwordConfirmation) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(password.bottom)
        bottom.linkTo(registerButton.top)
        width = Dimension.fillToConstraints
    }

    constrain(registerButton) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(passwordConfirmation.bottom)
        bottom.linkTo(parent.bottom)
        width = Dimension.fillToConstraints
    }

}
