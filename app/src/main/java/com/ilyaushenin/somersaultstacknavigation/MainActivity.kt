package com.ilyaushenin.somersaultstacknavigation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilyaushenin.somersaultstacknavigation.ui.theme.SomersaultStackNavigationTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val statesModal by viewModel.statesModal.collectAsState()

            SomersaultStackNavigationTheme {
                MainScreen(
                    states = statesModal,
//                    onNavigate = { state ->
//                        viewModel.navigateTo(state)
//                    },
//                    onBack = {
//                        viewModel.navigateBack()
//                    }
                )

            }
        }
    }
}



@Composable
fun MainScreen(
    states: StatesModal
) {
    val currentScreen = remember { mutableStateOf(states.somersaultStackNavigation.currentState()) }

    LaunchedEffect(states.somersaultStackNavigation.navigationStack) {
        currentScreen.value = states.somersaultStackNavigation.currentState()
    }

    if (states.x){
        Screen1(
            states = states,
            onNavigate = {
                states.somersaultStackNavigation.onForwardFlip(ScreenState.B)
                currentScreen.value = states.somersaultStackNavigation.currentState()
            }
        )
    }

    when (currentScreen.value) {
        ScreenState.A -> {
            Screen1(
                states = states,
                onNavigate = {
                    states.somersaultStackNavigation.onForwardFlip(ScreenState.B)
                    currentScreen.value = states.somersaultStackNavigation.currentState()
                }
            )
        }
        ScreenState.B -> {
            BoxB(
                onNavigateBack = {
                    states.somersaultStackNavigation.onBackFlip()
                    currentScreen.value = states.somersaultStackNavigation.currentState()
                }
            )
        }
        else -> {
            Text("Unknown Screen")
        }
    }
}

@Composable
fun Screen1(
    states: StatesModal,
    onNavigate: () -> Unit
) {
    Column {
        Text("Это Box A (Screen 1)")
        Button(onClick = onNavigate) {
            Text("Перейти на Box B")
        }
    }
}

@Composable
fun BoxB(
    onNavigateBack: () -> Unit
) {
    Column {
        Text("Это Box B (Screen 2)")
        Button(onClick = onNavigateBack) {
            Text("Назад на Box A")
        }
    }
}




//@Composable
//fun MainScreen(
//    states: StatesModal,
////    onNavigate: (Any) -> Unit,
////    onBack: () -> Unit
//) {
//
////    when (currentState) {
////        is Screens.BoxAState -> {
////            Screen1(
////                states = states,
////                onNavigate = {
////                    onNavigate(Screens.BoxBState())
////                }
////            )
////        }
////    }
//
//    if (states.x){
//        Screen1(
//            states = states,
//            onNavigate = {
//                states.somersaultStackNavigation.onForwardFlip(ScreenState.A)
//            }
//        )
//    }
//
//    when (states.somersaultStackNavigation.currentState()) {
//        ScreenState.A -> {
//            BoxB()
//        }
//    }
//}

//@Composable
//fun Screen1(
//    states: StatesModal,
//    onNavigate: () -> Unit
//) {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text("Box A", fontSize = 24.sp)
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(onClick = {
//            onNavigate()
//        }) {
//            Text("Перейти на Box B")
//        }
//    }
//}


@Composable
fun BoxB(

) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Box B", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
    }
}









//@Composable
//fun MainScreen(
//    states: StatesModal,
//    onNavigate: (Any) -> Unit,
//    onBack: () -> Unit
//) {
//    val currentState = states.somersaultStackNavigation.currentState()
//
//    when (currentState) {
//        is Screens.BoxAState -> {
//            BoxA(onNavigate = { onNavigate(Screens.BoxBState()) })
//        }
//        is Screens.BoxBState -> {
//            BoxB(onBack = { onBack() })
//        }
//        else -> {
//            BoxA(onNavigate = { onNavigate(BoxBState()) })
//        }
//    }
//}
//
//
//@Composable
//fun BoxA(onNavigate: () -> Unit) {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text("Box A", fontSize = 24.sp)
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(onClick = { onNavigate() }) {
//            Text("Перейти на Box B")
//        }
//    }
//}
//








//@Composable
//fun NavigationHandler(stackNav: SomersaultStackNavigation<Any>) {
//    // Следим за текущим состоянием стека с помощью remember и mutableStateOf
//    var currentState by remember { mutableStateOf(stackNav.currentState()) }
//
//    // Обновляем состояние UI при изменении стека
//    LaunchedEffect(stackNav) {
//        currentState = stackNav.currentState()
//    }
//
//    // Перехватываем нажатие кнопки "назад"
//    BackHandler(enabled = stackNav.navigationStack.size > 1) {
//        stackNav.onBackFlip()
//        currentState = stackNav.currentState() // Обновляем текущее состояние
//    }
//
//    // В зависимости от состояния, показываем нужный экран
//    when (currentState) {
//        is BoxAState -> {
//            BoxA {
//                // Переходим на Box B через onForwardFlip и обновляем состояние
//                stackNav.onForwardFlip(BoxBState())
//                currentState = stackNav.currentState() // Обновляем текущее состояние
//            }
//        }
//        is BoxBState -> {
//            BoxB {
//                // Возвращаемся на Box A через onBackFlip и обновляем состояние
//                stackNav.onBackFlip()
//                currentState = stackNav.currentState() // Обновляем текущее состояние
//            }
//        }
//    }
//}
//
//@Composable
//fun BoxA(onNavigate: () -> Unit) {
//    // Box A
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text("Box A", fontSize = 24.sp)
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(onClick = { onNavigate() }) {
//            Text("Перейти на Box B")
//        }
//    }
//}
//
//@Composable
//fun BoxB(onBack: () -> Unit) {
//    // Box B
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text("Box B", fontSize = 24.sp)
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(onClick = { onBack() }) {
//            Text("Назад на Box A")
//        }
//    }
//}

//class BoxAState
//class BoxBState
//
//@Composable
//fun AppContent() {
//    // Инициализируем навигацию
//    val stackNav = remember { SomersaultStackNavigation<Any>() }
//    // Добавляем начальное состояние в стек (Box A)
//    stackNav.onForwardFlip(BoxAState())
//
//    // Обрабатываем навигацию
//    NavigationHandler(stackNav = stackNav)
//}





