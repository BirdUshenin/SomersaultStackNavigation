package com.ilyaushenin.somersaultstacknavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ilyaushenin.somersaultstacknavigation.ui.theme.SomersaultStackNavigationTheme

class SampleActivity : ComponentActivity() {

    private val viewModel by viewModels<SampleActivityViewModel>()

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
