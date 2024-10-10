package com.ilyaushenin.somersaultstacknavigation.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WelcomeActivityScreen(
    onOpenSample: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Welcome to SomersaultStackNavigation!\n" +
                    "\n" +
                    "This is a sample app that demonstrates " +
                    "a custom navigation system designed to manage " +
                    "screen states in a convenient and flexible way. This " +
                    "library uses a navigation stack approach, where each new screen " +
                    "is added to the stack, and back navigation is easily managed without" +
                    " any extra code."
        )
        Button(
            modifier = Modifier
                .padding(30.dp)
                .width(300.dp)
                .height(60.dp),
            onClick = {
                onOpenSample()
            }
        ) {
            Text("Go to sample")
        }
    }
}