package com.rafalesan.credikiosko.home.presentation

import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.rafalesan.credikiosko.core.commons.presentation.theme.Dimens

sealed class Tag
internal data object BusinessNameLabel: Tag()
internal data object OptionsList: Tag()
internal data object CrediKioskoLabel: Tag()
internal data object VersionLabel: Tag()

internal val homeScreenConstraintSet = ConstraintSet {

    val businessNameLabel = createRefFor(BusinessNameLabel)
    val optionsList = createRefFor(OptionsList)
    val versionLabel = createRefFor(VersionLabel)
    val crediKioskoLabel = createRefFor(CrediKioskoLabel)

    constrain(businessNameLabel) {
        top.linkTo(parent.top, margin = Dimens.space2x)
        start.linkTo(parent.start, margin = Dimens.space2x)
        end.linkTo(parent.end, margin = Dimens.space2x)
        width = Dimension.fillToConstraints
        height = Dimension.wrapContent
    }

    constrain(optionsList) {
        top.linkTo(businessNameLabel.bottom)
        bottom.linkTo(parent.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }

    constrain(versionLabel) {
        end.linkTo(parent.end, margin = Dimens.spaceDefault)
        bottom.linkTo(parent.bottom, margin = Dimens.spaceDefault)
    }

    constrain(crediKioskoLabel) {
        start.linkTo(parent.start, margin = Dimens.spaceDefault)
        bottom.linkTo(parent.bottom, margin = Dimens.spaceDefault)
    }

}
