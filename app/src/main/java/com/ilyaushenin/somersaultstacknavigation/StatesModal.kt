package com.ilyaushenin.somersaultstacknavigation

data class StatesModal(
    val somersaultStackNavigation: SomersaultStackNavigation<Any>
    = SomersaultStackNavigation(),

    val x: Boolean = true
)

object MainScreens {
    class BoxAState
    class BoxBState
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

