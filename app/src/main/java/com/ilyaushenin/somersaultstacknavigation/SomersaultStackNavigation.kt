package com.ilyaushenin.somersaultstacknavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SomersaultStackNavigation {
    private val _navigationStack = MutableStateFlow(listOf<String>())
    val navigationStack: StateFlow<List<String>> get() = _navigationStack

    init {
        onForwardFlip("BoxA")
    }

    fun onForwardFlip(state: String) {
        _navigationStack.value += state
        println("Navigated Forward: $state, Stack: ${_navigationStack.value}")
    }

    fun onBackFlip(): String? {
        if (_navigationStack.value.size > 1) {
            _navigationStack.value = _navigationStack.value.dropLast(1)
        }
        val currentState = currentState()
        println("Navigated Back: ${currentState ?: "None"}")
        return currentState
    }

    private fun currentState(): String? = _navigationStack.value.lastOrNull()

    companion object {
        val LocalStackNav = compositionLocalOf<SomersaultStackNavigation> {
            error("StackNav not provided")
        }
    }

    fun canGoBack(): Boolean = _navigationStack.value.size > 1
}

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class NavigationScreen
object ScreenRegistry {
    private val screens
    = mutableMapOf<String, @Composable (SomersaultStackNavigation) -> Unit>()

    fun screen(
        key: String,
        content: @Composable (SomersaultStackNavigation) -> Unit
    ) {
        screens[key] = content
    }

    fun getScreen(
        key: String
    ): (@Composable (SomersaultStackNavigation) -> Unit)? {
        return screens[key]
    }
}
