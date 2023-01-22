package com.rafalesan.credikiosko.auth.login

import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension


sealed class Tag
internal object AppLogoTag: Tag()
internal object AppNameTag: Tag()
internal object LoginDescriptionTag: Tag()
internal object EmailTag: Tag()
internal object PasswordTag: Tag()
internal object LoginBtnTag: Tag()
internal object CreateAccountBtnTag: Tag()

internal val screenConstraintSet = ConstraintSet {
    val appLogo = createRefFor(AppLogoTag)
    val appName = createRefFor(AppNameTag)
    val loginDescription = createRefFor(LoginDescriptionTag)
    val email = createRefFor(EmailTag)
    val password = createRefFor(PasswordTag)
    val loginBtn = createRefFor(LoginBtnTag)
    val createAccountBtn = createRefFor(CreateAccountBtnTag)

    constrain(appLogo) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(parent.top)
        bottom.linkTo(appName.bottom)
    }

    constrain(appName) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(appLogo.bottom)
        bottom.linkTo(loginDescription.top)
    }

    constrain(loginDescription) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(appName.bottom)
        bottom.linkTo(email.top)
    }

    constrain(email) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(loginDescription.bottom)
        bottom.linkTo(password.top)
        width = Dimension.fillToConstraints
    }

    constrain(password) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(email.bottom)
        bottom.linkTo(loginBtn.top)
        width = Dimension.fillToConstraints
    }

    constrain(loginBtn) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(password.bottom)
        bottom.linkTo(createAccountBtn.top)
        width = Dimension.fillToConstraints
    }

    constrain(createAccountBtn) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(loginBtn.bottom)
        bottom.linkTo(parent.bottom)
        width = Dimension.fillToConstraints
    }

    createVerticalChain(
        appLogo,
        appName,
        loginDescription,
        email,
        password,
        loginBtn,
        createAccountBtn,
        chainStyle = ChainStyle.Packed
    )

}
