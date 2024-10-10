package com.ilyaushenin.somersaultstacknavigation.ui.theme

import androidx.compose.runtime.Composable
import com.ilyaushenin.somersault.NavigationScreens
import com.ilyaushenin.somersault.SomersaultStackNavigation

@NavigationScreens
object ScreenRegistry {
    private val screens = mutableMapOf<String, @Composable (SomersaultStackNavigation) -> Unit>()

    fun screen(key: String, content: @Composable (SomersaultStackNavigation) -> Unit) {
        screens[key] = content
    }

    fun getScreen(key: String): (@Composable (SomersaultStackNavigation) -> Unit)? {
        return screens[key]
    }
}