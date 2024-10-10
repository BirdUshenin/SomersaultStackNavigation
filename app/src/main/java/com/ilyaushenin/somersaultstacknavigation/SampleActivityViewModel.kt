package com.ilyaushenin.somersaultstacknavigation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SampleActivityViewModel : ViewModel() {
    private val _statesModal: MutableStateFlow<StatesModal>
    = MutableStateFlow(StatesModal())
    val statesModal = _statesModal.asStateFlow()
}