package com.ilyaushenin.somersaultstacknavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
        registerScreens()
    }
}

@Composable
fun AppContent(statesModal: StatesModal) {
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
    val currentStack by stackNav.navigationStack.collectAsState()

    BackHandler(enabled = stackNav.canGoBack()) {
        stackNav.onBackFlip()
    }

    val currentScreenKey = currentStack.lastOrNull()
    currentScreenKey?.let { key ->
        val screenContent = ScreenRegistry.getScreen(key)
        if (screenContent != null) {
            screenContent(stackNav)
        } else {
            Text("Unknown Screen")
        }
    } ?: run {
        println("No current screen to navigate to.")
    }
}

@NavigationScreens
fun registerScreens() {
    ScreenRegistry.screen("BoxA") { stackNav ->
        BoxA("This is Box A", stackNav)
    }

    ScreenRegistry.screen("BoxB") { stackNav ->
        BoxB("This is Box B", stackNav)
    }

    ScreenRegistry.screen("BoxC") { stackNav ->
        BoxC("This is Box C", stackNav)
    }
}

@Composable
fun BoxA(
    title: String,
    stackNav: SomersaultStackNavigation
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            stackNav.onForwardFlip("BoxB")
        }) {
            Text("Перейти на Box B")
        }

        Button(onClick = {
            stackNav.onBackFlip()
        }) {
            Text("Назад")
        }
    }
}

@Composable
fun BoxB(title: String, stackNav: SomersaultStackNavigation) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            stackNav.onForwardFlip("BoxC")
        }) {
            Text("Перейти на Box C")
        }

        Button(onClick = {
            stackNav.onBackFlip()
        }) {
            Text("Назад")
        }
    }
}

@Composable
fun BoxC(
    title: String,
    stackNav: SomersaultStackNavigation
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            stackNav.onForwardFlip("BoxA")
        }) {
            Text("Перейти на Box A")
        }

        Button(onClick = {
            stackNav.onBackFlip()
        }) {
            Text("Назад")
        }
    }
}