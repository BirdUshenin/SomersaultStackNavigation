package com.ilyaushenin.somersaultstacknavigation

import androidx.activity.compose.BackHandler
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.ilyaushenin.somersault.NavigationScreensGraph
import com.ilyaushenin.somersault.SomersaultStackNavigation

@Composable
fun AppContent(
    statesModal: StatesModal
) {
    // Your state class with implementation SomersaultStackNavigation
    val stackNav = remember { statesModal.somersaultStackNavigation }

    // You need to register a Navigation Graph in any main compose function
    registerScreens()

    // This is done for the correct use of CompositionLocalProvider.
    // In the CompositionLocalProvider you need to declare LocalStackNav.
    // It is necessary to support current instance anywhere in the composition tree.
    CompositionLocalProvider(
        SomersaultStackNavigation.LocalStackNav provides stackNav
    ) {
        // Pass the NavigationHandler in CompositionLocalProvider
        NavigationHandler()
    }
}

@Composable
fun NavigationHandler() {
    val stackNav = SomersaultStackNavigation.LocalStackNav.current
    val currentStack by stackNav.navigationStack.collectAsState()

    // Hang the handler back
    BackHandler(enabled = stackNav.canGoBack()) {
        stackNav.onBackFlip()
    }

    val currentScreenKey = currentStack.lastOrNull()
    currentScreenKey?.let { key ->
        val screenContent = ScreenRegistry.getScreen(key)
        screenContent?.invoke(stackNav) ?: Text("Error screen not fount.")
    } ?: run {
        Text("Backstack is clear.")
    }
}

/**
 * This is a Compose Navigation Graph
 */
@NavigationScreensGraph
fun registerScreens() {
    val screens: List<Pair<String, @Composable (SomersaultStackNavigation) -> Unit>> = listOf(
        "BoxA" to { stackNav -> BoxA("This is Box A", stackNav) },
        "BoxB" to { stackNav -> BoxB("This is Box B", stackNav) },
        "BoxC" to { stackNav -> BoxC("This is Box C", stackNav) }
    )

    screens.forEach { (key, screenContent) ->
        ScreenRegistry.screen(key, screenContent)
    }
}
