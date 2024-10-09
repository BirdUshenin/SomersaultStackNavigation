package com.ilyaushenin.somersaultstacknavigation

data class StatesModal(
    val somersaultStackNavigation: SomersaultStackNavigation<Any>
    = SomersaultStackNavigation(),

    val x: Boolean = true
)

sealed class MainScreen {
    data object BoxA : MainScreen()
    data object BoxB : MainScreen()
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

