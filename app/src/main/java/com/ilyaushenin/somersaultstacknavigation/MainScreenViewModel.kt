package com.ilyaushenin.somersaultstacknavigation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainScreenViewModel : ViewModel() {
    private val _statesModal: MutableStateFlow<StatesModal> = MutableStateFlow(StatesModal())
    val statesModal = _statesModal.asStateFlow()




//    fun navigateTo(state: Any) {
//        val currentNavigation = _statesModal.value.somersaultStackNavigation
//        currentNavigation.onForwardFlip(state)
//
//        _statesModal.value = _statesModal.value.copy(
//            somersaultStackNavigation = currentNavigation
//        )
//    }
//
//    fun navigateBack() {
//        val currentNavigation = _statesModal.value.somersaultStackNavigation
//        currentNavigation.onBackFlip()?.let { newState ->
//            _statesModal.value = _statesModal.value.copy(
//                somersaultStackNavigation = currentNavigation
//            )
//        }
//    }
}








