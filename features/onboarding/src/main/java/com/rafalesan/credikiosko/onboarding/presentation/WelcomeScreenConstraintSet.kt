package com.rafalesan.credikiosko.onboarding.presentation

import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

sealed class Tag

internal data object AppLogoTag: Tag()
internal data object WelcomeTitleTag: Tag()
internal data object WelcomeDescriptionTag: Tag()
internal data object BusinessNameInputTag: Tag()
internal data object ContinueButtonTag: Tag()

internal val screenConstraintSet = ConstraintSet {

    val appLogo = createRefFor(AppLogoTag)
    val welcomeTitle = createRefFor(WelcomeTitleTag)
    val welcomeDescription = createRefFor(WelcomeDescriptionTag)
    val businessNameInput = createRefFor(BusinessNameInputTag)
    val continueButton = createRefFor(ContinueButtonTag)

    constrain(appLogo) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(parent.top)
        bottom.linkTo(welcomeTitle.top)
    }

    constrain(welcomeTitle) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(appLogo.bottom)
        bottom.linkTo(welcomeDescription.top)
        width = Dimension.fillToConstraints
    }

    constrain(welcomeDescription) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(welcomeTitle.bottom)
        bottom.linkTo(businessNameInput.top)
        width = Dimension.fillToConstraints
    }

    constrain(businessNameInput) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(welcomeDescription.bottom)
        bottom.linkTo(continueButton.top)
        width = Dimension.fillToConstraints
    }


    constrain(continueButton) {
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        top.linkTo(businessNameInput.bottom)
        bottom.linkTo(parent.bottom)
        width = Dimension.fillToConstraints
    }

    createVerticalChain(
        appLogo,
        welcomeTitle,
        welcomeDescription,
        businessNameInput,
        continueButton,
        chainStyle = ChainStyle.Packed
    )

}
