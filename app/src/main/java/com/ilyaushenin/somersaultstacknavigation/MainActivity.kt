package com.ilyaushenin.somersaultstacknavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilyaushenin.somersaultstacknavigation.ui.theme.SomersaultStackNavigationTheme

//class MainActivity : ComponentActivity() {
//    private val viewModel by viewModels<MainScreenViewModel>()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            val statesModal by viewModel.statesModal.collectAsState()
//
//            SomersaultStackNavigationTheme {
//                AppContent()
//            }
//        }
//    }
//}
//
//@Composable
//fun AppContent() {
//    // Инициализируем навигацию
//    val stackNav = remember { SomersaultStackNavigation<Any>() }
//    // Добавляем начальное состояние в стек (Box A)
//    stackNav.onForwardFlip(MainScreens.BoxAState())
//
//    // Обрабатываем навигацию
//    NavigationHandler(stackNav = stackNav)
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
//        is MainScreens.BoxAState -> {
//            BoxA {
//                // Переходим на Box B через onForwardFlip и обновляем состояние
//                stackNav.onForwardFlip(MainScreens.BoxBState())
//                currentState = stackNav.currentState() // Обновляем текущее состояние
//            }
//        }
//        is MainScreens.BoxBState -> {
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


class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val statesModal by viewModel.statesModal.collectAsState()

            SomersaultStackNavigationTheme {
                AppContent(statesModal)
            }
        }
    }
}

@Composable
fun AppContent(statesModal: StatesModal) {
    // Инициализируем навигацию один раз с помощью remember
    val stackNav = remember { statesModal.somersaultStackNavigation }

    // Добавляем начальное состояние в стек, если стек пуст
    LaunchedEffect(Unit) {
        if (stackNav.navigationStack.isEmpty()) {
            stackNav.onForwardFlip(MainScreen.BoxA)
        }
    }

    NavigationHandler(stackNav = stackNav)
}

@Composable
fun NavigationHandler(stackNav: SomersaultStackNavigation<Any>) {
    var currentState by remember { mutableStateOf(stackNav.currentState()) }

    LaunchedEffect(stackNav.navigationStack) {
        currentState = stackNav.currentState()
    }

    BackHandler(enabled = stackNav.navigationStack.size > 1) {
        stackNav.onBackFlip()
        currentState = stackNav.currentState()
    }

    when (currentState) {
        MainScreen.BoxA -> {
            BoxA {
                // Инициализация БоксА в стеке
                MainScreen.BoxA.navigateTo(stackNav)
                currentState = stackNav.currentState()
            }
        }
        MainScreen.BoxB -> {
            BoxB {
                // Инициализация БоксБ в стеке
                MainScreen.BoxB.navigateTo(stackNav)
                currentState = stackNav.currentState()
            }
        }
    }
}

@Composable
fun BoxA(onNavigate: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Box A", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigate) {
            Text("Перейти на Box B")
        }
    }
}

@Composable
fun BoxB(onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Box B", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) {
            Text("Назад на Box A")
        }
    }
}