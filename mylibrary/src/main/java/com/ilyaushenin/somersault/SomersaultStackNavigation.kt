package com.ilyaushenin.somersault

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SomersaultStackNavigation(initialState: String) {

    /***
     * @param _navigationStack is a private property representing the navigation stack.
     * It stores a list of states (screens) as a `MutableStateFlow`,
     * which allows tracking stack changes in a reactive manner
     * and automatically updating the UI when changes occur.
     ***/
    private val _navigationStack = MutableStateFlow(listOf(initialState))

    val navigationStack: StateFlow<List<String>> get() = _navigationStack

    /***
     * This method is used when you need to navigate to a new screen in your application
     * by adding the corresponding state to the stack.
     ***/
    fun onForwardFlip(state: String) {
        _navigationStack.value += state
    }

    /***
     * This method can be used to navigate back through the app's screens
     * and update the UI according to the current state of the stack.
     ***/
    fun onBackFlip(): String? {
        if (_navigationStack.value.size > 1) {
            _navigationStack.value = _navigationStack.value.dropLast(1)
        }
        val currentState = currentState()
        return currentState
    }

    /***
     * This method is useful for getting the current screen in the navigation system
     * and displaying the correct interface to the user.
     ***/
    private fun currentState(): String? = _navigationStack.value.lastOrNull()

    /***
     * @param LocalStackNav This is an object of type `CompositionLocal`,
     * created using the `compositionLocalOf` function.
     * It is used to provide access to a `SomersaultStackNavigation` instance
     * anywhere in the composition tree.
     ***/
    companion object {
        val LocalStackNav = compositionLocalOf<SomersaultStackNavigation> {
            error("StackNav not provided")
        }
    }

    /***
     * The `canGoBack()` method determines whether the user can go back to the previous screen.
     ***/
    fun canGoBack(): Boolean = _navigationStack.value.size > 1
}

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class NavigationScreens
object ScreenRegistry {
    private val screens
            = mutableMapOf<String, @Composable (SomersaultStackNavigation) -> Unit>()

    fun screen(
        key: String,
        content: @Composable (SomersaultStackNavigation) -> Unit
    ) {
        screens[key] = content
    }

    fun getScreen(key: String): (@Composable (SomersaultStackNavigation) -> Unit)? {
        return screens[key] as? (@Composable (SomersaultStackNavigation) -> Unit)
    }
}