package com.ilyaushenin.somersaultstacknavigation.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import com.ilyaushenin.somersaultstacknavigation.SampleActivity
import com.ilyaushenin.somersaultstacknavigation.ui.theme.SomersaultStackNavigationTheme

class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val samplePage = Intent(this, SampleActivity::class.java)

        setContent {
            SomersaultStackNavigationTheme {
                Column {
                    WelcomeActivityScreen(
                        onOpenSample = { startActivity(samplePage) }
                    )
                }
            }
        }
    }
}