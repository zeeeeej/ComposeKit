package com.zeeeeej.compose.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zeeeeej.compose.common.color.randomColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onUserLoginIn: (Int) -> Unit, viewModel: LoginViewModel = viewModel()) {
    var count by remember {
        mutableIntStateOf(0)
    }
    Box(
        Modifier
            .fillMaxSize()
            .background(randomColor()), contentAlignment = Alignment.Center
    ) {
        Column {
            Button(onClick = {
                count++
            }) {
                Text(text = "count++")
            }

            Button(onClick = {
                viewModel.login()
            }) {
                Text(text = "Log in")
            }
        }

    }


    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val state by viewModel.state.collectAsState()
    val currentOnUserLoginIn by rememberUpdatedState(newValue = onUserLoginIn)
    LaunchedEffect(viewModel, lifecycle) {
        snapshotFlow {
            state
        }.filter {
            it.isUserLoggedIn == true
        }.flowWithLifecycle(lifecycle)
            .collect {
                viewModel.loginInEd()
//                onUserLoginIn(count)
                currentOnUserLoginIn(count)

            }
    }
}

@Composable
fun LoginScreenAdvance(onUserLoginIn: (Int) -> Unit, viewModel: LoginViewModel = viewModel()) {
    var count by remember {
        mutableIntStateOf(0)
    }
    var validateInLogin by rememberSaveable {
        mutableStateOf(false)
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(randomColor()), contentAlignment = Alignment.Center
    ) {
        Column {
            Button(onClick = {
                count++
            }) {
                Text(text = "count++")
            }

            Button(onClick = {
                viewModel.login()
                validateInLogin = true
            }) {
                Text(text = "Log in")
            }
        }

    }


    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val state by viewModel.state.collectAsState()
    val currentOnUserLoginIn by rememberUpdatedState(newValue = onUserLoginIn)
    LaunchedEffect(viewModel, lifecycle) {
        snapshotFlow {
            state
        }.filter {
            it.isUserLoggedIn == true && validateInLogin
        }.flowWithLifecycle(lifecycle)
            .collect {
//                viewModel.loginInEd()
                currentOnUserLoginIn(count)
                validateInLogin = false
            }
    }
}


data class LoginState(
    val isUserLoggedIn: Boolean? = null,
    val index: Int = 0
)

class LoginViewModel : ViewModel() {
    private val _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun login() {
        viewModelScope.launch {
            delay(1000)
            _state.value = state.value.copy(isUserLoggedIn = true, index = state.value.index + 1)
        }
    }

    fun loginInEd() {
        _state.value = state.value.copy(isUserLoggedIn = null)
    }
}

