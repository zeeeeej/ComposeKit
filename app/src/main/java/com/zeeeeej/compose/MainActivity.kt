package com.zeeeeej.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zeeeeej.compose.common.color.randomColor
import com.zeeeeej.compose.common.dialog.XLoadingDialog
import com.zeeeeej.compose.screen.LoginScreen
import com.zeeeeej.compose.screen.LoginScreenAdvance
import com.zeeeeej.compose.ui.theme.ComposeKitTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeKitTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "ComposeKit",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    var loading: String by remember {
        mutableStateOf("")
    }

    var text: String by remember {
        mutableStateOf("")
    }

    val rememberCoroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        var toLogin:Boolean? by remember {
            mutableStateOf(null)
        }
        Text(
            text = "Hello $name! $text",
            modifier = modifier
                .background(randomColor().copy(alpha = .5f))
                .clickable {
//                    text = "点击了一下"
//                    rememberCoroutineScope.launch {
//                        snackbarHostState.showSnackbar("哈哈哈")
//                        loading = "正在努力加载中2"
//                    }
                    toLogin = true

                }
        )

        if (loading.isNotBlank()) {
            XLoadingDialog(loading) {
                loading = ""
            }
        }
        SnackbarHost(hostState = snackbarHostState)

        if (toLogin == true){
//            LoginScreen(onUserLoginIn = {
//                text = "$text->$it"
//                toLogin = null
//            })
            LoginScreenAdvance(onUserLoginIn = {
                text = "$text->$it"
                toLogin = null
            })
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeKitTheme {
        Greeting("Android")
    }
}