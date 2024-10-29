package com.ilyaushenin.somersaultstacknavigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilyaushenin.somersault.NavigationScreensGraph
import com.ilyaushenin.somersault.ScreenRegistry
import com.ilyaushenin.somersault.SomersaultStackNavigation
import com.ilyaushenin.somersaultstacknavigation.decorations.ParkingPost

@Composable
fun AppContent(
    statesModal: StatesModal
) {
    // Your state class with implementation SomersaultStackNavigation
    val stackNav = remember { statesModal.somersaultStackNavigation }
    RegisterScreens()

    Column (modifier = Modifier){
        Box(modifier = Modifier
            .size(760.dp)
            .background(Color.DarkGray),
        ){
            CompositionLocalProvider(SomersaultStackNavigation.LocalStackNav provides stackNav) {
                // Pass the NavigationHandler in CompositionProvider
                NavigationHandler()
            }
        }

        BlockD(
            stackNav = stackNav
        )
    }
    // You need to register a Navigation Graph in any main compose function


    // This is done for the correct use of CompositionLocalProvider.
    // In the CompositionLocalProvider you need to declare LocalStackNav.
    // It is necessary to support current instance anywhere in the composition tree.


}


@Composable
@Preview
fun Preview(){
//    BlockD(
//        stackNav = SomersaultStackNavigation("ParkingPlace")
//    )

}

@Composable
fun BlockD(
    stackNav: SomersaultStackNavigation<String>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray),
        Arrangement.SpaceBetween
    ) {
        Button(onClick = {
            stackNav.onForwardFlip("BlockC")
        }) { Text("C") }

        Button(onClick = {
            stackNav.onForwardFlip("BlockA")
        }) { Text("A") }

        Button(onClick = {
            stackNav.onForwardFlip("BlockB")
        }) { Text("B") }
    }
}

@Composable
fun NavigationHandler() {
    val stackNavCurrent = SomersaultStackNavigation.LocalStackNav.current
    val stackNav = SomersaultStackNavigation.getNavigation<String>(stackNavCurrent)
    val currentStack by stackNav.navigationStack.collectAsState()

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
fun RegisterScreens() {
    val screens: List<Pair<String, @Composable (SomersaultStackNavigation<String>) -> Unit>>
    = listOf(
        "ParkingPlace" to { stackNav -> ParkingPlace("This is Parking", stackNav) },
        "BlockA" to { stackNav -> BlockA("This is Block A", stackNav) },
        "BlockB" to { stackNav -> BlockB("This is Block B", stackNav) },
        "BlockC" to { stackNav -> BlockC("This is Block C", stackNav) }
    )

    screens.forEach { (key, screenContent) ->
        ScreenRegistry.screen(key, screenContent)
    }
}