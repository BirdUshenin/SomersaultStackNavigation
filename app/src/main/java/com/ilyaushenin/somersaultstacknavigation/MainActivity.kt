package com.ilyaushenin.somersaultstacknavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilyaushenin.somersaultstacknavigation.ui.theme.SomersaultStackNavigationTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val statesModal by viewModel.statesModal.collectAsState()

            SomersaultStackNavigationTheme {
                AppContent(statesModal)
            }
        }
    }
}

@Composable
fun AppContent(
    statesModal: StatesModal
) {
    val stackNav = remember { statesModal.somersaultStackNavigation }

    CompositionLocalProvider(
        SomersaultStackNavigation.LocalStackNav provides stackNav
    ) {
        NavigationHandler()
    }
}

@Composable
fun NavigationHandler() {

    val stackNav = SomersaultStackNavigation.LocalStackNav.current
    var currentState by remember { mutableStateOf(stackNav.currentState()) }

    LaunchedEffect(stackNav.navigationStack) {
        currentState = stackNav.currentState()
    }

    BackHandler(enabled = stackNav.canGoBack()) {
        currentState = stackNav.onBackFlip()
    }

    when (currentState) {
        MainScreen.BoxA -> {
            BoxA{
                currentState = stackNav.updateCurrentState()
            }
        }

        MainScreen.BoxB -> {
            BoxB {
                currentState = stackNav.updateCurrentState()
            }
        }

        MainScreen.BoxC -> {
            BoxC {
                currentState = stackNav.updateCurrentState()
            }
        }
    }
}

//@Composable
//fun NavigationHandler(stackNav: SomersaultStackNavigation<String>) {
//    val currentState = stackNav.currentState()
//    currentState?.let { key ->
//        ScreenRegistry.getScreen(key)?.invoke(stackNav) ?: Text("Unknown Screen")
//    }
//}
//
//
//fun registerScreens() {
//    ScreenRegistry.registerScreen("BoxA") { stackNav ->
//        BoxContent("This is Box A", stackNav)
//    }
//
//    ScreenRegistry.registerScreen("BoxB") { stackNav ->
//        BoxContent("This is Box B", stackNav)
//    }
//
//    ScreenRegistry.registerScreen("BoxC") { stackNav ->
//        BoxContent("This is Box C", stackNav)
//    }
//}


@Composable
fun BoxA(
    onNavigate: () -> Unit
) {
    val stackNav = SomersaultStackNavigation.LocalStackNav.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Box A", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            MainScreen.BoxB.navigateTo(stackNav)
            onNavigate()
        }) {
            Text("Перейти на Box B")
        }
        Button(onClick = {
            MainScreen.BoxC.navigateTo(stackNav)
            onNavigate()
        }) {
            Text("Перейти на Box C")
        }
    }
}


@Composable
fun BoxB(
    onNavigate: () -> Unit
) {
    val stackNav = SomersaultStackNavigation.LocalStackNav.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Box B", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            MainScreen.BoxC.navigateTo(stackNav)
            onNavigate()
        }) {
            Text("Перейти на Box C")
        }
    }
}

@Composable
fun BoxC(
    onNavigate: () -> Unit
) {
    val stackNav = SomersaultStackNavigation.LocalStackNav.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Box B", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            MainScreen.BoxB.navigateToBack(stackNav)
            onNavigate()
        }) {
            Text("Перейти на Box A")
        }
    }
}