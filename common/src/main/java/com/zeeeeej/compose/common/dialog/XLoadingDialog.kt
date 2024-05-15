package com.zeeeeej.compose.common.dialog

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import kotlinx.coroutines.delay

@Composable
fun XLoadingDialog(
    text: String = "加载中",
    properties: DialogProperties = DialogProperties(
        decorFitsSystemWindows = true,
        usePlatformDefaultWidth = true,
        dismissOnBackPress = false,
        dismissOnClickOutside = true,
        securePolicy = SecureFlagPolicy.Inherit
    ),
    debug: Boolean = false,
    dimAmount: Float = .5f,
    onDismissRequest: () -> Unit,
) {
    XDialog(properties, dimAmount = dimAmount, onDismissRequest = onDismissRequest) {
        Box(
            Modifier
                .size(DialogDefaults.DEFAULT_SIZE)
                .clip(DialogDefaults.DEFAULT_SHAPE)
                .padding(16.dp)

        ) {
            Column(
                Modifier
                    .wrapContentSize()
                    .align(Alignment.Center)
            ) {
                var end by remember {
                    mutableStateOf("")
                }
                LaunchedEffect(key1 = Unit) {
                    while (true) {
                        delay(500)
                        end += "."
                        if (end.length > 3) {
                            end = "."
                        }
                    }
                }

                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "$text$end",
                    fontSize = DialogDefaults.DEFAULT_FONT_Size,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)

                        .animateContentSize(spring(Spring.DampingRatioHighBouncy))
                )
            }
        }
    }
}

internal object DialogDefaults {
    internal val DEFAULT_FONT_Size = 12.sp
    internal val DEFAULT_SIZE = 150.dp
    internal val DEFAULT_SHAPE = RoundedCornerShape(12.dp)
}