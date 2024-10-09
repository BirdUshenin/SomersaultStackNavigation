package com.ilyaushenin.somersaultstacknavigation

data class StatesModal(
    val somersaultStackNavigation: SomersaultStackNavigation<Any>
    = SomersaultStackNavigation(MainScreen.BoxB),
)

@NavigationScreen
sealed class MainScreen : Navigatable {
    data object BoxA : MainScreen()
    data object BoxB : MainScreen()
    data object BoxC : MainScreen()

    override fun navigateTo(stackNav: SomersaultStackNavigation<Any>) {
        stackNav.onForwardFlip(this)
    }

    override fun navigateToBack(stackNav: SomersaultStackNavigation<Any>) {
        stackNav.onBackFlip()
    }
}