package com.rafalesan.credikiosko.home

import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens

sealed class Tag
internal data object WelcomeLabel: Tag()
internal data object BusinessLabel: Tag()
internal data object ProfilePhoto: Tag()
internal data object OptionsList: Tag()

internal val homeScreenConstraintSet = ConstraintSet {

    val welcomeLabel = createRefFor(WelcomeLabel)
    val businessLabel = createRefFor(BusinessLabel)
    val profilePhoto = createRefFor(ProfilePhoto)
    val optionsList = createRefFor(OptionsList)

    constrain(profilePhoto) {
        top.linkTo(parent.top, margin = Dimens.space2x)
        end.linkTo(parent.end, margin = Dimens.space2x)
    }

    val welcomeBusinessLabelsVerticalChain = createVerticalChain(
        welcomeLabel,
        businessLabel,
        chainStyle = ChainStyle.Packed
    )

    constrain(welcomeBusinessLabelsVerticalChain) {
        top.linkTo(profilePhoto.top)
        bottom.linkTo(profilePhoto.bottom)
    }

    constrain(welcomeLabel) {
        top.linkTo(profilePhoto.top)
        bottom.linkTo(businessLabel.top)
        start.linkTo(parent.start, margin = Dimens.space2x)
        end.linkTo(profilePhoto.start, margin = Dimens.space2x)
        width = Dimension.fillToConstraints
    }

    constrain(businessLabel) {
        top.linkTo(welcomeLabel.bottom)
        bottom.linkTo(profilePhoto.bottom)
        start.linkTo(parent.start, margin = Dimens.space2x)
        end.linkTo(profilePhoto.start, margin = Dimens.space2x)
        width = Dimension.fillToConstraints
    }

    constrain(optionsList) {
        top.linkTo(businessLabel.bottom)
        bottom.linkTo(parent.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }

}
