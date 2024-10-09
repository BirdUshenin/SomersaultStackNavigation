package com.ilyaushenin.somersaultstacknavigation

data class StatesModal(
    val somersaultStackNavigation: SomersaultStackNavigation<Any>
    = SomersaultStackNavigation(),

    val x: Boolean = true
)

//@NavigationScreen
//sealed class MainScreen {
//    data object BoxA : MainScreen()
//    data object BoxB : MainScreen()
//}

@NavigationScreen
sealed class MainScreen : Navigatable {
    data object BoxA : MainScreen() {
        override fun navigateTo(stackNav: SomersaultStackNavigation<Any>) {
            stackNav.onForwardFlip(BoxB)
        }
    }

    data object BoxB : MainScreen() {
        override fun navigateTo(stackNav: SomersaultStackNavigation<Any>) {
            stackNav.onBackFlip()
        }
    }
}


//data class ScreenState(
//    val screenType: String,
//    val args: Map<String, Any> = emptyMap()
//)

//enum class ScreenState {
//    A,
//    B,
//    D,
//    L
//}

