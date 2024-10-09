package com.ilyaushenin.somersaultstacknavigation

data class StatesModal(
    val somersaultStackNavigation: SomersaultStackNavigation<Any>
    = SomersaultStackNavigation(MainScreen.BoxA),
)

@NavigationScreen
sealed class MainScreen : Navigatable {
    data object BoxA : MainScreen()
    data object BoxB : MainScreen()
    data object BoxC : MainScreen()

    internal fun navigateTo(stackNav: SomersaultStackNavigation<Any>) {
        stackNav.onForwardFlip(this)
    }

    internal fun navigateToBack(stackNav: SomersaultStackNavigation<Any>) {
        stackNav.onBackFlip()
    }
}

