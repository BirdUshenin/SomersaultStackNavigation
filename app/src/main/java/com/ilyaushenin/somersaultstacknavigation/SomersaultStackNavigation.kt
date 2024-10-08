package com.ilyaushenin.somersaultstacknavigation

//class SomersaultStackNavigation {
//
//    fun onBackFlip() { }
//    fun onForwardFlip() { }
//}

class SomersaultStackNavigation<T> {
    val navigationStack = mutableListOf<T>()

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
}
