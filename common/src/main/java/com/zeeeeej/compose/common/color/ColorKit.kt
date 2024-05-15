package com.zeeeeej.compose.common.color

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

/**
 * 随机颜色[androidx.compose.ui.graphics.Color]
 */
fun randomColor(alpha: Float = 1f) = Color(
    alpha = alpha,
    red = Random.nextFloat(),
    green = Random.nextFloat(),
    blue = Random.nextFloat(),
)

