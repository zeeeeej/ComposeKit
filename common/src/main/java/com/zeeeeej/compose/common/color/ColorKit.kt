package com.zeeeeej.compose.common.color

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun randomColor(alpha: Float = 1f) = Color(
    alpha = alpha,
    red = Random.nextFloat(),
    green = Random.nextFloat(),
    blue = Random.nextFloat(),
)

fun android.graphics.Color.convert(): Color = Color(
    alpha = this.alpha(),
    red = this.red(),
    blue = this.blue(),
    green = this.green()
)

fun Color.convert(): android.graphics.Color =
    android.graphics.Color.valueOf(this.red, this.green, this.blue, this.alpha)

