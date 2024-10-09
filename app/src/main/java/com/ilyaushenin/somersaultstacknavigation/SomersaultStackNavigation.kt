package com.ilyaushenin.somersaultstacknavigation

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

}

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class NavigationScreen
interface Navigatable {
    fun navigateTo(stackNav: SomersaultStackNavigation<Any>)
    fun navigateToBack(stackNav: SomersaultStackNavigation<Any>)
}