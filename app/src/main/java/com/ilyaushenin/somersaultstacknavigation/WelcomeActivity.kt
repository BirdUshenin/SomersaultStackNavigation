package com.ilyaushenin.somersaultstacknavigation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ilyaushenin.somersaultstacknavigation.ui.theme.SomersaultStackNavigationTheme

class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SomersaultStackNavigationTheme {
                Button(
                    modifier = Modifier
                        .size(300.dp),
                    onClick = {
                        val changePage = Intent(this, MainActivity::class.java)
                        startActivity(changePage)
                    }
                ) { }
            }
        }
    }
}