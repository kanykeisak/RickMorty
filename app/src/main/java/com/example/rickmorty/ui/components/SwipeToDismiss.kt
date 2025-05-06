package com.example.rickmorty.ui.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

fun <T> Modifier.swipeToDismiss(
    item: T,
    onDismiss: (T) -> Unit
): Modifier = composed {
    val offsetX = remember { mutableStateOf(0f) }
    val density = LocalDensity.current

    val transition = updateTransition(offsetX.value, label = "swipeTransition")
    val scale by transition.animateFloat(
        label = "scale",
        targetValueByState = { offset ->
            if (offset < 0) 1f - (offset / 1000f) else 1f
        }
    )

    this
        .offset { IntOffset(offsetX.value.roundToInt(), 0) }
        .scale(scale)
        .pointerInput(Unit) {
            detectHorizontalDragGestures(
                onDragEnd = {
                    if (offsetX.value < -100) {
                        onDismiss(item)
                    }
                    offsetX.value = 0f
                },
                onHorizontalDrag = { _, dragAmount ->
                    if (dragAmount < 0) {
                        offsetX.value += dragAmount
                    }
                }
            )
        }
} 