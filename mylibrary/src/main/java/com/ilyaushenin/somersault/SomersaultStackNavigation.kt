package com.ilyaushenin.somersault

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.unit.Dp

class SomersaultStackNavigation<T>(initialState: T) {

    /**
     * Represents the navigation stack.
     * It stores a list of states (screens) as a [MutableStateFlow],
     * which allows tracking stack changes in a reactive manner
     * and automatically updating the UI when changes occur.
     */
    val navigationStack: StateFlow<List<T>> get() = _navigationStack
    private val _navigationStack = MutableStateFlow(listOf(initialState))

    /**
     * Navigates forward through the app's screens.
     * It can be used to navigate to a new screen in the application by adding the corresponding
     * state to the navigation stack.
     */
    fun onForwardFlip(state: T) {
        _navigationStack.value = _navigationStack.value.let { currentStack ->
            if (currentStack.lastOrNull() == state) {
                currentStack.dropLast(1) + state
            } else {
                currentStack + state
            }
        }
    }

    /**
     * Navigates forward through the app's screens
     * with the impossibility of going back up the stack.
     */
    fun onForwardFlipPoint(state: T) {
        clearStack()
        _navigationStack.value = _navigationStack.value.let { currentStack ->
            if (currentStack.lastOrNull() == state) {
                currentStack.dropLast(1) + state
            } else {
                currentStack + state
            }
        }
    }

    /**
     * Navigates back through the app's screens.
     * It can be used to navigate back in the application and update the UI according to
     * the current state of the navigation stack.
     */
    fun onBackFlip(): T? {
        if (canGoBack()){
            _navigationStack.value = _navigationStack.value.dropLast(1)
        }
        return currentState()
    }

    /**
     * Get the current state in the navigation stack.
     * It can be used to get the current state and display it on the screen.
     */
    private fun currentState(): T? = _navigationStack.value.lastOrNull()

    companion object {
        /**
         * Provides access to [SomersaultStackNavigation] instance anywhere in the composition tree.
         */
        val LocalStackNav = compositionLocalOf<SomersaultStackNavigation<*>> {
            error("StackNav not provided")
        }
        inline fun <reified T> getNavigation(stackNav: SomersaultStackNavigation<*>)
                : SomersaultStackNavigation<T> {
            return stackNav as? SomersaultStackNavigation<T>
                ?: error("Expected SomersaultStackNavigation<${T::class.java}> but found different type")
        }
    }

    /**
     * Determines whether it is possible to go back to the previous screen.
     */
    fun canGoBack(): Boolean = _navigationStack.value.size > 1

    /**
     * Clears the entire navigation stack, leaving only the initial state.
     */
    fun clearStack() {
        _navigationStack.value = emptyList()
    }
}

object ScreenRegistry {
    /**
     * These methods are responsible for registering your screens that you will create.
     */
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

/**
 * Register your screens with list.
 *
 * Example:
 * RegisterScreens(listOf("BlockA" to { stackNav -> BlockA("This is Block A", stackNav) }))
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class NavigationScreensGraph
@Composable
fun RegisterScreens(
    screens: List<Pair<String, @Composable (SomersaultStackNavigation<String>) -> Unit>>
) {
    screens.forEach { (key, screenContent) ->
        ScreenRegistry.screen(key, screenContent)
    }
}

@Composable
fun SomersaultBottomNavigation(
    stackNav: SomersaultStackNavigation<String>,
    backgroundShape: Dp,
    screens: List<ScreenItem>,
    colorEnabled: Color? = null,
    colorDisabled: Color? = null,
    backgroundInFocus: Color? = null,
    backgroundOutFocus: Color? = null,
    iconColorEnabled: Color? = null,
    iconColorDisabled: Color? = null,
) {
    val lastKnownScreen = remember { mutableStateOf<String?>(null) }

    val currentStack by stackNav.navigationStack.collectAsState()
    val currentScreen =
        currentStack.lastOrNull()?.takeIf { screen -> screens.any { it.key == screen } }
            ?: lastKnownScreen.value

    if (screens.any { it.key == currentScreen }) {
        lastKnownScreen.value = currentScreen
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        screens.forEach { screen ->
            BottomNavButton(
                screen = screen,
                currentScreen = currentScreen,
                stackNav = stackNav,
                colorEnabled = colorEnabled,
                colorDisabled = colorDisabled,
                backgroundInFocus = backgroundInFocus,
                backgroundOutFocus = backgroundOutFocus,
                iconColorEnabled = iconColorEnabled,
                iconColorDisabled = iconColorDisabled,
                backgroundShape = backgroundShape,
            )
        }
    }
}

@Composable
fun BottomNavButton(
    stackNav: SomersaultStackNavigation<String>,
    backgroundShape: Dp,
    screen: ScreenItem,
    currentScreen: String?,
    onScreenSelected: ((String) -> Unit)? = null,
    colorEnabled: Color? = null,
    colorDisabled: Color? = null,
    backgroundInFocus: Color? = null,
    backgroundOutFocus: Color? = null,
    iconColorEnabled: Color? = null,
    iconColorDisabled: Color? = null,
) {
    val isSelected = screen.key == currentScreen

    Button(
        onClick = {
            onScreenSelected?.invoke(screen.key) ?: stackNav.onForwardFlipPoint(screen.key)
        },
        colors = ButtonDefaults.buttonColors(
            if (isSelected) {
                backgroundInFocus ?: Color(0xFF00AAC0)
            } else {
                backgroundOutFocus ?: Color.Transparent
            }
        ),
        shape = RoundedCornerShape(backgroundShape)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            screen.icon(
                color = if (isSelected) {
                    iconColorEnabled ?: Color(0xFFFFFFFF)
                } else {
                    iconColorDisabled ?: Color(0xFF000000)
                }
            )
            Text(
                text = screen.label,
                color = if (isSelected) {
                    colorEnabled ?: Color(0xFFFFFFFF)
                } else {
                    colorDisabled ?: Color(0xFF000000)
                }
            )
        }
    }
}

data class ScreenItem(
    val key: String,
    val label: String,
    val icon: @Composable (color: Color) -> Unit
)