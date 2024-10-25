package com.ilyaushenin.somersault

import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SomersaultStackNavigation<T>(initialState: T) {

    /**
     * Represents the navigation stack.
     * Stores a list of states (screens) as a [MutableStateFlow],
     * allowing tracking stack changes in a reactive manner
     * and automatically updating the UI when changes occur.
     */
    val navigationStack: StateFlow<List<T>> get() = _navigationStack
    private val _navigationStack = MutableStateFlow(listOf(initialState))

    /**
     * Navigates forward by adding a new state to the stack.
     */
    fun onForwardFlip(state: T) {
        _navigationStack.value += state
    }

    /**
     * Navigates back by removing the last state from the stack.
     */
    fun onBackFlip(): T? {
        if (_navigationStack.value.size > 1) {
            _navigationStack.value = _navigationStack.value.dropLast(1)
        }
        return currentState()
    }

    /**
     * Gets the current state in the navigation stack.
     */
    private fun currentState(): T? = _navigationStack.value.lastOrNull()

    companion object {
        /**
         * Provides access to a [SomersaultStackNavigation] instance anywhere in the composition tree.
         */
        val LocalStackNav = compositionLocalOf<SomersaultStackNavigation<*>> {
            error("StackNav not provided")
        }
    }

    /**
     * Determines whether it is possible to go back to the previous state.
     */
    fun canGoBack(): Boolean = _navigationStack.value.size > 1
}


@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class NavigationRoute

/**
 * Create methods to store the state of screens, as well as their list.
 *
 * ```
 * object ScreenRegistry {
 *     private val screens = mutableMapOf<String, @Composable (SomersaultStackNavigation) -> Unit>()
 *
 *     fun screen(key: String, content: @Composable (SomersaultStackNavigation) -> Unit) {
 *         screens[key] = content
 *     }
 *
 *     fun getScreen(key: String): (@Composable (SomersaultStackNavigation) -> Unit)? {
 *         return screens[key]
 *     }
 * }
 * ```
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class NavigationScreensGraph
/**
 * Create methods to your @Compose function
 *
 * ```
 * fun registerScreens() {
 *     val screens: List<Pair<String, @Composable (SomersaultStackNavigation) -> Unit>> = listOf(
 *         "BoxA" to { stackNav -> BoxA("This is Box A", stackNav) },
 *         "BoxB" to { stackNav -> BoxB("This is Box B", stackNav) },
 *         "BoxC" to { stackNav -> BoxC("This is Box C", stackNav) }
 *     )
 *
 *     screens.forEach { (key, screenContent) ->
 *         ScreenRegistry.screen(key, screenContent)
 *     }
 * }
 * ```
 */
