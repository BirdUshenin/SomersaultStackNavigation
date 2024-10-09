package com.ilyaushenin.somersaultstacknavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

class SomersaultStackNavigation<T>(initialState: T) {

    val navigationStack = mutableListOf<T>()

    init {
        onForwardFlip(initialState)
    }

    fun onForwardFlip(state: T) {
        navigationStack.add(state)
        println("Navigated Forward: ${state}, Stack: $navigationStack")
    }

    fun onBackFlip(): T? {
        if (navigationStack.size > 1) {
            navigationStack.removeAt(navigationStack.size - 1)
        }
        val currentState = currentState()
        println("Navigated Back: ${currentState?.let { it::class.simpleName }}")
        return currentState
    }

    fun currentState(): T? = navigationStack.lastOrNull()

    companion object {
        val LocalStackNav = compositionLocalOf<SomersaultStackNavigation<Any>> {
            error("StackNav not provided")
        }
    }

    fun updateCurrentState() = currentState()

    fun canGoBack(): Boolean = navigationStack.size > 1

}

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class NavigationScreen
interface Navigatable

object ScreenRegistry {
    private val screens = mutableMapOf<String, @Composable (SomersaultStackNavigation<String>) -> Unit>()

    fun registerScreen(key: String, content: @Composable (SomersaultStackNavigation<String>) -> Unit) {
        screens[key] = content
    }

    fun getScreen(key: String): (@Composable (SomersaultStackNavigation<String>) -> Unit)? {
        return screens[key]
    }
}
