package com.ilyaushenin.somersaultstacknavigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaushenin.somersault.NavigationScreensGraph
import com.ilyaushenin.somersault.RegisterScreens
import com.ilyaushenin.somersault.ScreenItem
import com.ilyaushenin.somersault.ScreenRegistry
import com.ilyaushenin.somersault.SomersaultBottomNavigation
import com.ilyaushenin.somersault.SomersaultStackNavigation

@Composable
fun AppContent(
    statesModal: StatesModal
) {
    // Your state class with implementation SomersaultStackNavigation
    val stackNav = remember { statesModal.somersaultStackNavigation }

    Column(modifier = Modifier) {
        Box(
            modifier = Modifier
                .weight(1f)
                .background(Color.DarkGray),
        ) {
            // This is done for the correct use of CompositionLocalProvider.
            // In the CompositionLocalProvider you need to declare LocalStackNav.
            // It is necessary to support current instance anywhere in the composition tree.
            CompositionLocalProvider(SomersaultStackNavigation.LocalStackNav provides stackNav) {
                // Pass the NavigationHandler in CompositionProvider
                NavigationHandler()
            }
        }
        SomersaultBottomNavigation(
            stackNav = stackNav,
            backgroundShape = 25.dp,
            iconColorEnabled = Color.White,
            iconColorDisabled = Color.Black,
            screens = listOf(
                ScreenItem(
                    key = "BlockA",
                    label = "Block A",
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Block A",
                            tint = it
                        )
                    }
                ),
                ScreenItem(
                    key = "BlockB",
                    label = "Block B",
                    icon = {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = "Block B",
                            tint = it
                        )
                    }
                ),
                ScreenItem(
                    key = "BlockC",
                    label = "Block C",
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = null,
                            tint = it
                        )
                    },
                ),
            )
        )
    }
}

@Composable
fun NavigationHandler() {
    val stackNavCurrent = SomersaultStackNavigation.LocalStackNav.current
    val stackNav = SomersaultStackNavigation.getNavigation<String>(stackNavCurrent)
    val currentStack by stackNav.navigationStack.collectAsState()

    // You need to register a Navigation Graph in NavigationHandler
    AppRegisterScreens()

    // Hang the handler back
    BackHandler(enabled = stackNav.canGoBack()) {
        stackNav.onBackFlip()
    }

    val currentScreenKey = currentStack.lastOrNull()
    currentScreenKey?.let { key ->
        val screenContent = ScreenRegistry.getScreen(key)
        screenContent?.invoke(stackNav) ?: Text("Error screen not found.")
    } ?: run {
        Text("Backstack is clear.")
    }
}

/**
 * This is a Compose Navigation Graph
 */
@NavigationScreensGraph
@Composable
fun AppRegisterScreens() {
    RegisterScreens(
        listOf(
            "ParkingPlace" to { stackNav -> ParkingPlace("This is Parking", stackNav) },
            "BlockA" to { stackNav -> BlockA("This is Block A", stackNav) },
            "BlockB" to { stackNav -> BlockB("This is Block B", stackNav) },
            "BlockC" to { stackNav -> BlockC("This is Block C", stackNav) }
        )
    )
}

@Composable
@Preview
fun Preview() {
    AppContent(StatesModal())
}