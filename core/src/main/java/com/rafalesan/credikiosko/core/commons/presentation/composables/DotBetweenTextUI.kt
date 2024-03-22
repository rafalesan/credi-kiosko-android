package com.rafalesan.credikiosko.core.commons.presentation.composables

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import kotlin.math.floor

@Composable
fun DotBetweenTextUI(
    modifier: Modifier = Modifier,
    start: String,
    end: String,
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        val composableWidth = maxWidth
        val textMeasurer = rememberTextMeasurer()

        val startWidth = textMeasurer.measure(
            text = start,
        ).size.width.pxToDp()
        val endWidth = textMeasurer.measure(
            text = end,
        ).size.width.pxToDp()
        val dotWidth = textMeasurer.measure(
            text = ".",
        ).size.width.pxToDp()

        val composableWidthInFloat = floor(composableWidth.value)

        val startAndEndComposableWidthWithPadding = (startWidth.value) + (endWidth.value)

        val calculatedNumberOfDots =
            ((composableWidthInFloat - startAndEndComposableWidthWithPadding) / dotWidth.value).toInt()
        val numberOfDots = if (calculatedNumberOfDots < 0) {
            0
        } else {
            calculatedNumberOfDots
        }
        val dotText = ".".repeat(numberOfDots)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = start,
            )
            Text(
                text = dotText,
                maxLines = 1,
                modifier = Modifier.weight(1F),
            )
            Text(
                text = end,
            )
        }
    }
}

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }