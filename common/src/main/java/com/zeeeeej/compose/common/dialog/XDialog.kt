package com.zeeeeej.compose.common.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy

@Composable
fun XDialog(
    properties: DialogProperties = DialogProperties(
        decorFitsSystemWindows = true,
        usePlatformDefaultWidth = true,
        dismissOnBackPress = true,
        dismissOnClickOutside = true,
        securePolicy = SecureFlagPolicy.Inherit
    ),
    dimAmount: Float = .1f,
    onDismissRequest: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
) {

    XPop(contentAlignment = Alignment.Center, onDismiss = {
        if (properties.dismissOnClickOutside) {
            onDismissRequest()
        }
    }, dimAmount = dimAmount, content = {
        Box(modifier = Modifier
            .widthIn(max= 160.dp,min  = 100.dp)
            .wrapContentWidth()
            .aspectRatio(1f)
            .background(Color.White), contentAlignment = Alignment.Center) {
            content()
        }
    })

}
