package com.ilyaushenin.somersaultstacknavigation

import androidx.compose.runtime.Composable
import com.ilyaushenin.somersault.NavigationRoute
import com.ilyaushenin.somersault.SomersaultStackNavigation

/**
 * Implement an object with which you will register all your screens.
 */
@NavigationRoute
object ScreenRegistry {
    private val screens = mutableMapOf<String, @Composable (
        SomersaultStackNavigation<String>
    ) -> Unit>()

    fun screen(key: String, content: @Composable (SomersaultStackNavigation<String>) -> Unit) {
        screens[key] = content
    }

    fun getScreen(key: String): (@Composable (SomersaultStackNavigation<String>) -> Unit)? {
        return screens[key]
    }
}