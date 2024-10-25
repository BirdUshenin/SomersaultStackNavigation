package com.ilyaushenin.somersault

import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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
        _navigationStack.value += state
    }

    /**
     * Navigates back through the app's screens.
     * It can be used to navigate back in the application and update the UI according to
     * the current state of the navigation stack.
     */
    fun onBackFlip(): T? {
        if (_navigationStack.value.size > 1) {
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
}

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class NavigationRoute

/**
 * Create methods to store the state of screens, as well as their list.
 *
 * ```
 * object ScreenRegistry {
 *     private val screens = mutableMapOf<String, @Composable (SomersaultStackNavigation<T>) -> Unit>()
 *
 *     fun screen(key: String, content: @Composable (SomersaultStackNavigation<T>) -> Unit) {
 *         screens[key] = content
 *     }
 *
 *     fun getScreen(key: String): (@Composable (SomersaultStackNavigation<T>) -> Unit)? {
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
 *     val screens: List<Pair<String, @Composable (SomersaultStackNavigation<T>) -> Unit>> = listOf(
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